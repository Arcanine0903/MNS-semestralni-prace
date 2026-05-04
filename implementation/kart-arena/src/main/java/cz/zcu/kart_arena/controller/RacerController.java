package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.Racer;
import cz.zcu.kart_arena.service.RacerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controller for User-related operations.
 */
@RestController
@RequestMapping("/api/racers")
public class RacerController {

    private final RacerService racerService;

    public RacerController(RacerService racerService) {
        this.racerService = racerService;
    }

    /**
     * Data Transfer Object for registration request.
     * @param name - racer's full name
     * @param username - racer's chosen username
     * @param password - racer's chosen password
     * @param birthday - racer's birthday
     * @param city - racer's city of residence
     * @param address - racer's address
     * @param phoneNumber - racer's phone number
     */
    public record RegistrationRequest(
            String name,
            String username,
            String password,
            LocalDate birthday,
            String city,
            String address,
            String phoneNumber
    ) {}

    /**
     * End point for registering a new user.
     * @param request - registration request
     * @return a response indicating success or failure of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        try {
            Racer savedRacer = racerService.registerRacer(
                    request.name(),
                    request.username(),
                    request.password(),
                    request.birthday(),
                    request.city(),
                    request.address(),
                    request.phoneNumber()
            );
            return ResponseEntity.ok("Závodník " + savedRacer.getName() + " byl úspěšně zaregistrován!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Bans a racer by setting their isRestricted property to true.
     * @param id - id of the racer to ban
     * @return a response indicating success or failure of the ban
     */
    @PostMapping("/{id}/ban")
    public ResponseEntity<String> banRacer(@PathVariable Long id) {
        try {
            racerService.banRacer(id);
            return ResponseEntity.ok("Racer has been successfully banned.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}