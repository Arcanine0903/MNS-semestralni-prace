package cz.zcu.kart_arena.repository;

import cz.zcu.kart_arena.model.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for Racer class.
 */
@Repository
public interface RacerRepository extends JpaRepository<Racer, Long> {


    /**
     * Finds racers by name or username.
     * @param name - name of the racer
     * @param username - username of the racer
     * @return List of racers matching the search criteria
     */
    List<Racer> findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(String name, String username);
}