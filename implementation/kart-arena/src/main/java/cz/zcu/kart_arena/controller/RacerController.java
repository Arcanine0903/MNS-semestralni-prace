package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.Racer;
import cz.zcu.kart_arena.model.dto.RacerDto;
import cz.zcu.kart_arena.model.dto.RegistrationRequestDto;
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

    public RacerController(RacerService racerService) {
        this.racerService = racerService;
    }

    /**
     * End point for registering a new user.
     * @param request - registration request
     * @return a response indicating success or failure of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequestDto request) {
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
            return ResponseEntity.ok("Races " + savedRacer.getName() + " has been successfully registered!");
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
     * Unbans a racer by setting their isRestricted property to false.
     * @param id - id of the racer to unban
     * @return a response indicating success or failure of the unbanning
     */
    @PostMapping("/{id}/unban")
    public ResponseEntity<String> unbanRacer(@PathVariable Long id) {
        try {
            racerService.unbanRacer(id);
            return ResponseEntity.ok("Racer has been successfully unbanned.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


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