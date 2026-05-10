package cz.zcu.kart_arena.repository;

import cz.zcu.kart_arena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User abstract class.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks if the user with the given username exists
     * @param username - username to check
     * @return True if the user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Finds the user with the given username
     * @param username - username to search for
     * @return Optional User object
     */
    Optional<User> findByUsername(String username);
}
