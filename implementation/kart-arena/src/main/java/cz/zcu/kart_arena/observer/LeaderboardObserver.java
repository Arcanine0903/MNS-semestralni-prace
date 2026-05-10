package cz.zcu.kart_arena.observer;

import cz.zcu.kart_arena.model.RaceResult;
import cz.zcu.kart_arena.model.dto.RaceResultDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class LeaderboardObserver implements RaceResultObserver {

    // Paměť otevřených spojení: Kteří uživatelé (SseEmitters) sledují jaký závod (Long raceId)
    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    // 1. Metoda, kterou zavolá Controller, když uživatel otevře stránku závodu v Reactu
    public SseEmitter subscribeToRace(Long raceId) {
        SseEmitter emitter = new SseEmitter(0L); // 0L - connection never expires

        emitters.computeIfAbsent(raceId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        // Uklidíme paměť, pokud uživatel zavře prohlížeč
        emitter.onCompletion(() -> emitters.get(raceId).remove(emitter));
        emitter.onTimeout(() -> emitters.get(raceId).remove(emitter));

        return emitter;
    }

    @Override
    public void onNewRaceResult(RaceResult newResult) {
        Long raceId = newResult.getRace().getId();
        List<SseEmitter> raceWatchers = emitters.get(raceId);

        if (raceWatchers != null) {

            RaceResultDto dtoToSend = new RaceResultDto(
                    newResult.getId(),
                    newResult.getRacer().getUsername(),
                    newResult.getKart().getNumber(),
                    newResult.getBestTime()
            );

            for (SseEmitter emitter : raceWatchers) {
                try {
                    emitter.send(SseEmitter.event()
                            .name("new-result")
                            .data(dtoToSend));
                } catch (IOException e) {
                    emitter.complete();
                    raceWatchers.remove(emitter);
                }
            }
        }
    }
}
