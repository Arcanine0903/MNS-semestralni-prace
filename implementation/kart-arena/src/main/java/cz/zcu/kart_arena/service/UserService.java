package cz.zcu.kart_arena.service;

import cz.zcu.kart_arena.model.User;
import cz.zcu.kart_arena.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for User-related operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user.
     * @param username - chosen username
     * @param email - chosen email
     * @param password - chosen password
     * @return newly created user
     */
    public User registerUser(String username, String email, String password) {

        // Check for duplicate username and email
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-mail is already used.");
        }

        // Creates the new user and saves him to the database
        User newUser = new User(username, email, password);
        return userRepository.save(newUser);
    }
}