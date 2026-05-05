package cz.zcu.kart_arena.repository;

import cz.zcu.kart_arena.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Repository for Race class.
 */
@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    /**
     * Checks if a race with a given track, date, and time already exists.
     * @param track - track of the race
     * @param raceDate - date of the race
     * @param raceTime - time of the race
     * @return True if race exists, false otherwise
     */
    boolean existsByTrackAndRaceDateAndRaceTime(String track, LocalDate raceDate, LocalTime raceTime);

}