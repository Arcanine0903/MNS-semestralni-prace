package cz.zcu.kart_arena.repository;

import cz.zcu.kart_arena.model.Race;
import cz.zcu.kart_arena.model.RaceResult;
import cz.zcu.kart_arena.model.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {

    /**
     * Checks if a submission with identical data already exists.
     * @param race - race of the submission
     * @param racer - racer of the submission
     * @return True if a submission exists, false otherwise
     */
    boolean existsByRaceAndRacer(Race race, Racer racer);

}
