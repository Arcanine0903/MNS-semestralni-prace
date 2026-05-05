package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.Racer;
import cz.zcu.kart_arena.repository.RacerRepository;
import cz.zcu.kart_arena.service.RacerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for Racer-related operations.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/racers")
public class RacerController {

    private final RacerService racerService;

    public RacerController(RacerService racerService, RacerRepository racerRepository) {
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

    /**
     * Data Transfer Object for racer details.
     * @param id - racer's id
     * @param name - racer's full name
     * @param username - racer's username
     * @param city - racer's city of residence
     * @param isRestricted - is the racer restricted?
     */
    public record RacerDto(
            Long id,
            String name,
            String username,
            String city,
            boolean isRestricted
    ) {}

    /**
     * Provides a full list of racers.
     * @param search - search query (can be empty to show all racers)
     * @return a list of racers matching the search criteria
     */
    @GetMapping
    public ResponseEntity<List<RacerDto>> getAllRacers(@RequestParam(required = false) String search) {

        List<RacerDto> racers = racerService.getRacersList(search);
        return ResponseEntity.ok(racers);
    }
}