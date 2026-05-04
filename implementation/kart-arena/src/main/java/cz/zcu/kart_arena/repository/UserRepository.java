package cz.zcu.kart_arena.repository;

import cz.zcu.kart_arena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for User entity
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
     * Checks if the user with the given email exists
     * @param email - email to check
     * @return True if the user exists, false otherwise
     */
    boolean existsByEmail(String email);
}
