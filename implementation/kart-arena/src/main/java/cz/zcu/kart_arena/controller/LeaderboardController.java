package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.observer.LeaderboardObserver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Controller for handling leaderboard-related operations.
 */
@RestController
@RequestMapping("/api/races")
@CrossOrigin(origins = "*")
public class LeaderboardController {

    private final LeaderboardObserver observer;

    /**
     * LeaderboardController class constructor.
     * @param Observer - observer to notify when a new race result is available.
     */
    public LeaderboardController(LeaderboardObserver Observer) {
        this.observer = Observer;
    }

    /**
     * Stream live results of a race.
     * @param raceId - id of the race
     * @return - SSEEmitter object for streaming live results
     */
    @GetMapping(value = "/{raceId}/live", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamLiveResults(@PathVariable Long raceId) {
        return observer.subscribeToRace(raceId);
    }
}
