package cz.zcu.kart_arena.service;

import cz.zcu.kart_arena.model.Racer;
import cz.zcu.kart_arena.model.User;
import cz.zcu.kart_arena.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service class for Racer-related operations.
 */
@Service
public class RacerService {

    private final UserRepository userRepository;

    public RacerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new racer in the database.
     * @param name - racer's full name
     * @param username - racer's chosen username
     * @param password - racer's chosen password
     * @param birthday - racer's birthday
     * @param city - racer's city of residence
     * @param address - racer's address
     * @param phoneNumber - racer's phone number
     * @return a new Racer class instance
     */
    public Racer registerRacer(String name, String username, String password, LocalDate birthday, String city, String address, String phoneNumber) {

        // Check for duplicate username
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Creates the new racer and saves him to the database
        Racer newRacer = new Racer(name, username, password, birthday, city, address, phoneNumber);
        return userRepository.save(newRacer);
    }

    /**
     * Bans a racer by setting their isRestricted property to true.
     * @param id - id of the racer to ban
     */
    public void banRacer(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Racer not found.") );

        if (user instanceof Racer racer) {
            racer.ban();

            userRepository.save(racer);
        } else throw new IllegalArgumentException("User is not a racer.");
    }
}