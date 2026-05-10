package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.dto.RaceControlPayloadDto;
import cz.zcu.kart_arena.service.RaceResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling race results.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/telemetry")
public class RaceResultController {

    private final RaceResultService raceResultService;

    /**
     * RaceResultController class constructor.
     * @param raceResultService - service for RaceResult-related operations.
     */
    public RaceResultController(RaceResultService raceResultService) {
        this.raceResultService = raceResultService;
    }

    /**
     * Endpoint for receiving race results.
     * @param payload - JSON payload containing race result data.
     * @return a response indicating success or failure of the operation.
     */
    @PostMapping("/results")
    public ResponseEntity<String> receiveResult(@RequestBody RaceControlPayloadDto payload) {
        try {
            // The data is given to the service for processing.
            String message = raceResultService.processIncomingResult(
                    payload.raceId(), payload.racerId(), payload.kartId(),
                    payload.placingStart(), payload.placingEnd(), payload.bestTime()
            );

            // Response by service (payload with identical data classed as a successful operation)
            return ResponseEntity.ok(message);

        } catch (IllegalArgumentException e) {
            // Corrupted/Invalid data
            return ResponseEntity.badRequest().body("Error in the race result attributes: " + e.getMessage());
        }
    }
}