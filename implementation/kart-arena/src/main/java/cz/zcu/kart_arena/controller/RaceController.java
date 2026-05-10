package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.Race;
import cz.zcu.kart_arena.model.dto.RaceDto;
import cz.zcu.kart_arena.model.dto.RaceResultDto;
import cz.zcu.kart_arena.model.dto.RaceSetupRequestDto;
import cz.zcu.kart_arena.service.RaceService;
import cz.zcu.kart_arena.service.RaceResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controller for Race-related operations.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/races")
public class RaceController {

    private final RaceService raceService;
    private final RaceResultService raceResultService;

    /**
     * RaceController class constructor.
     * @param raceService - service for Race-related operations.
     */
    public RaceController(RaceService raceService, RaceResultService raceResultService) {
        this.raceService = raceService;
        this.raceResultService = raceResultService;
    }

    /**
     * Endpoint for setting up a new race.
     * @param request - race setup request
     * @return a response indicating success or failure of the race setup.
     */
    @PostMapping("/setup")
    public ResponseEntity<String> setupRace(@RequestBody RaceSetupRequestDto request) {
        try {
            Race savedRace = raceService.setupRace(
                    request.raceDate(),
                    request.raceTime(),
                    request.track()
            );

            // Race successfully setup - Return 200 OK with the ID of the new race.
            return ResponseEntity.ok("Race on track " + savedRace.getTrack() + " successfully created with ID: " + savedRace.getId());

        } catch (IllegalArgumentException e) {
            // Invalid race setup data - Return 400 Bad Request with the error message.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint for getting a list of all races.
     * @return a list of all races.
     */
    @GetMapping
    public ResponseEntity<List<RaceDto>> getRaces(){
        List<RaceDto> races = raceService.getRacesList();

        return ResponseEntity.ok(races);
    }

    /**
     * Endpoint for getting race results.
     * @param raceId - id of the race
     * @return a list of race results
     */
    @GetMapping("/{raceId}/results")
    public ResponseEntity<List<RaceResultDto>> getRaceResults(@PathVariable Long raceId) {
        List<RaceResultDto> results = raceResultService.getResultsForRace(raceId);
        return ResponseEntity.ok(results);
    }
}