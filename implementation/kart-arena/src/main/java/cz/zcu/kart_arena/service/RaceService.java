package cz.zcu.kart_arena.service;

import cz.zcu.kart_arena.model.Race;
import cz.zcu.kart_arena.repository.RaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Service class for Race-related operations.
 */
@Service
public class RaceService {

    private final RaceRepository raceRepository;

    /**
     * RaceService class constructor.
     * @param raceRepository - repository for Race class.
     */
    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
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

        return raceRepository.save(newRace);
    }
}