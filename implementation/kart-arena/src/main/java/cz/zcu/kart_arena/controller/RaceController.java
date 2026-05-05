package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.Race;
import cz.zcu.kart_arena.service.RaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Controller for Race-related operations.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/races")
public class RaceController {

    private final RaceService raceService;

    /**
     * RaceController class constructor.
     * @param raceService - service for Race-related operations.
     */
    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    /**
     * DTO for race setup request.
     * @param raceDate - date of the race
     * @param raceTime - time of the race
     * @param track - track where the race takes place
     */
    public record RaceSetupRequest(
            LocalDate raceDate,
            LocalTime raceTime,
            String track
    ) {}


    /**
     * Endpoint for setting up a new race.
     * @param request - race setup request
     * @return a response indicating success or failure of the race setup.
     */
    @PostMapping("/setup")
    public ResponseEntity<String> setupRace(@RequestBody RaceSetupRequest request) {
        try {
            Race savedRace = raceService.setupRace(
                    request.raceDate(),
                    request.raceTime(),
                    request.track()
            );

            // Race successfully setup - Return 200 OK with the ID of the new race.
            return ResponseEntity.ok("Race on track " + savedRace.getTrack() + " was successfully created with ID: " + savedRace.getId());

        } catch (IllegalArgumentException e) {
            // Invalid race setup data - Return 400 Bad Request with the error message.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}