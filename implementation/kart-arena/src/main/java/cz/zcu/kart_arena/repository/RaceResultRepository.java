package cz.zcu.kart_arena.repository;

import cz.zcu.kart_arena.model.Race;
import cz.zcu.kart_arena.model.RaceResult;
import cz.zcu.kart_arena.model.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for RaceResult class.
 */
@Repository
public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {

    /**
     * Checks if a submission with identical data already exists.
     * @param race - race of the submission
     * @param racer - racer of the submission
     * @return True if a submission exists, false otherwise
     */
    boolean existsByRaceAndRacer(Race race, Racer racer);

    /**
     * Finds all race results for a given race.
     * @param raceId - id of the race
     * @return List of RaceResult objects
     */
    List<RaceResult> findByRaceId(Long raceId);
}
