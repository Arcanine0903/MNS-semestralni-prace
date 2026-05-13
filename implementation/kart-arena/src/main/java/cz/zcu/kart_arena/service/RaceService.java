package cz.zcu.kart_arena.service;

import cz.zcu.kart_arena.client.RaceControlClient;
import cz.zcu.kart_arena.controller.RaceController;
import cz.zcu.kart_arena.controller.RacerController;
import cz.zcu.kart_arena.model.Race;
import cz.zcu.kart_arena.model.dto.RaceControlSetupDto;
import cz.zcu.kart_arena.repository.RaceRepository;
import org.springframework.stereotype.Service;
import cz.zcu.kart_arena.model.dto.RaceDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Service class for Race-related operations.
 */
@Service
public class RaceService {

    private final RaceRepository raceRepository;
    private final RaceControlClient raceControlClient;

    /**
     * RaceService class constructor.
     * @param raceRepository - repository for Race class.
     * @param raceControlClient - client for communicating with the RaceControl.
     */
    public RaceService(RaceRepository raceRepository, RaceControlClient raceControlClient) {
        this.raceRepository = raceRepository;
        this.raceControlClient =  raceControlClient;
    }

    /**
     * Creates a new race and saves it to the database.
     * @param raceDate - date of the race
     * @param raceTime - time of the race
     * @param track - track where the race takes place
     * @return the newly created Race object
     */
    public Race setupRace(LocalDate raceDate, LocalTime raceTime, String track) {

        // Race cannot be scheduled in the past.
        LocalDateTime raceDateTime = LocalDateTime.of(raceDate, raceTime);
        if (raceDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid data: The race cannot be scheduled in the past.");
        }

        // Race cannot be scheduled on the same track as another race at the same time.
        if (raceRepository.existsByTrackAndRaceDateAndRaceTime(track, raceDate, raceTime)) {
            throw new IllegalArgumentException("Invalid data: On track '" + track + "' is a race already scheduled at this time.");
        }

        Race newRace = new Race(raceDate, raceTime, track);

        // Mocked communication with the RaceControl.
        RaceControlSetupDto setupDto = new RaceControlSetupDto(newRace.getId(), newRace.getTrack(), newRace.getRaceTime());
        raceControlClient.sendRaceSetup(setupDto);

        return raceRepository.save(newRace);
    }

    /**
     * Returns a list of races.
     * @return a list of races.
     */
    public List<RaceDto> getRacesList() {
        List<Race> races = raceRepository.findAll();

        return races.stream()
                .map(race -> new RaceDto(
                        race.getId(),
                        race.getRaceTime()
                ))
                .toList();
    }
}
