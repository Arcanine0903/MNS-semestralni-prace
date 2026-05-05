package cz.zcu.kart_arena.config;

import cz.zcu.kart_arena.model.Employee;
import cz.zcu.kart_arena.model.Racer;
import cz.zcu.kart_arena.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * Configuration class for initializing the database with default data.
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            // Check if the default user already exists
            if (!userRepository.existsByUsername("admin")) {

                // Create a new default employee
                Employee defaultEmployee = new Employee(
                        "admin",           // username
                        "admin123",        // password
                        "Jan",             // name
                        "Novak",           // surname
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

                // Save him to the database
                userRepository.save(defaultEmployee);
                userRepository.save(defaultRacer);
                userRepository.save(defaultRacer2);

                System.out.println("Default employee " + defaultEmployee.getName() + " created successfully.");
                System.out.println("Default racer " + defaultRacer.getName() + " created successfully.");
                System.out.println("Default racer " + defaultRacer2.getName() + " created successfully.");
            }
        };
    }
}