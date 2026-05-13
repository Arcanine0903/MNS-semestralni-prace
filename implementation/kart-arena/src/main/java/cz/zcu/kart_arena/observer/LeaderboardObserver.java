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

/**
 * Observer class for receiving race results and sending them to subscribed clients.
 */
@Component
public class LeaderboardObserver implements RaceResultObserver {

    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribeToRace(Long raceId) {
        SseEmitter emitter = new SseEmitter(0L); // 0L - connection never expires

        emitters.computeIfAbsent(raceId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        // Memory cleanup
        emitter.onCompletion(() -> emitters.get(raceId).remove(emitter));
        emitter.onTimeout(() -> emitters.get(raceId).remove(emitter));

        return emitter;
    }

    /**
     * Notifies all subscribers about a new race result.
     * @param newResult - race result object
     */
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
