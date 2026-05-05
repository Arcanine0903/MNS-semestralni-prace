package cz.zcu.kart_arena.service;

import cz.zcu.kart_arena.model.Employee;
import cz.zcu.kart_arena.model.Racer;
import cz.zcu.kart_arena.model.User;
import cz.zcu.kart_arena.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service class for authentication-related operations.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user and returns their role.
     * @param username - entered username
     * @param password - entered password
     * @return - role of the user
     */
    public String login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getPassword().equals(password)) {


                // Checks if the user is an instance of the Racer class
                if (user instanceof Racer) {
                    Racer racer = (Racer) user;

                    // Checks if the racer is restricted.
                    if (racer.isRestricted()) {
                        throw new IllegalArgumentException("Your account has been suspended.");
                    }
                    return "RACER";
                }

                // Checks if the user is an instance of the Employee class
                if (user instanceof Employee) {
                    return "EMPLOYEE";
                }

            } else {
                throw new IllegalArgumentException("Incorrect password.");
            }
        }

        throw new IllegalArgumentException("An user with this username does not exist.");
    }
}