package cz.zcu.kart_arena.config;

import cz.zcu.kart_arena.model.*;
import cz.zcu.kart_arena.repository.KartRepository;
import cz.zcu.kart_arena.repository.RaceRepository;
import cz.zcu.kart_arena.repository.RaceResultRepository;
import cz.zcu.kart_arena.repository.UserRepository;
import cz.zcu.kart_arena.service.RaceResultService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Configuration class for initializing the database with default data.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, RaceRepository raceRepository, RaceResultRepository raceResultRepository, KartRepository kartRepository) {
        return args -> {
            // Check if the default user already exists
            if (!userRepository.existsByUsername("admin")) {

                // Create a new default employee
                Employee defaultEmployee = new Employee(
                        "admin",  // username
                        "admin123",        // password
                        "Jan Burak",       // name
                        "900101/1234",     // birthCertificateNumber
                        "Praha",           // city
                        "Praha 1",         // address
                        "789456123"        // phoneNumber
                );

                Racer defaultRacer = new Racer(
                        "Jan Francak",
                        "ministr",
                        "asdasd",
                        LocalDate.now(),
                        "As",
                        "Nekde v Asi",
                        "123456789"
                );

                Racer defaultRacer2 = new Racer(
                        "Jan Novak",
                        "nejsemMinistr",
                        "asdasd",
                        LocalDate.now(),
                        "Cheb",
                        "Nekde v Chlebu",
                        "123456789"
                );

                Racer defaultRacer3 = new Racer(
                        "Jan Ptak",
                        "ptacekloskutacek",
                        "asdasd",
                        LocalDate.now(),
                        "Praha",
                        "Praga 1",
                        "123456789"
                );

                // Save him to the database
                userRepository.save(defaultEmployee);

                userRepository.save(defaultRacer);
                userRepository.save(defaultRacer2);
                userRepository.save(defaultRacer3);

                System.out.println("Default employee " + defaultEmployee.getName() + " created successfully.");
                System.out.println("Default racer " + defaultRacer.getName() + " created successfully.");
                System.out.println("Default racer " + defaultRacer2.getName() + " created successfully.");
                System.out.println("Default racer " + defaultRacer3.getName() + " created successfully.");

                Race defaultRace = new Race(
                        LocalDate.now(),
                        LocalTime.now(),
                        "Track 1"
                );

                Kart defaultKart = new Kart(
                        "red",
                        1
                );

                Kart defaultKart2 = new Kart(
                        "blue",
                        2
                );

                Kart defaultKart3 = new Kart(
                        "green",
                        3
                );

                kartRepository.save(defaultKart);
                kartRepository.save(defaultKart2);
                kartRepository.save(defaultKart3);
                raceRepository.save(defaultRace);

                System.out.println("Default race " + defaultRace.getId() + " created successfully.");
                System.out.println("Default kart " + defaultKart.getId() + " created successfully.");
                System.out.println("Default kart " + defaultKart2.getId() + " created successfully.");
                System.out.println("Default kart " + defaultKart3.getId() + " created successfully.");

                /* RaceResult result1 = new RaceResult(1,
                        1,
                        10000L,
                        false,
                        defaultRacer,
                        defaultRace,
                        defaultKart
                );

                RaceResult result2 = new RaceResult(1,
                        2,
                        15000L,
                        false,
                        defaultRacer2,
                        defaultRace,
                        defaultKart2
                );

                raceResultRepository.save(result1);
                raceResultRepository.save(result2);

                System.out.println("Default race result " + result1.getId() + " created successfully.");
                System.out.println("Default race result " + result2.getId() + " created successfully."); */


            }
        };
    }
}