package cz.zcu.kart_arena.service;

import cz.zcu.kart_arena.model.*;
import cz.zcu.kart_arena.model.dto.RaceResultDto;
import cz.zcu.kart_arena.observer.RaceResultObserver;
import cz.zcu.kart_arena.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceResultService {

    private final RaceResultRepository resultRepository;
    private final RaceRepository raceRepository;
    private final UserRepository userRepository;
    private final KartRepository kartRepository;

    private final List<RaceResultObserver> observers;

    /**
     * RaceResultService class constructor.
     * @param resultRepository - repository for RaceResult class.
     * @param raceRepository - repository for Race class.
     * @param userRepository - repository for User class.
     * @param kartRepository - repository for Kart class.
     * @param observers - list of observers to notify when a new race result is available.
     */
    public RaceResultService(RaceResultRepository resultRepository, RaceRepository raceRepository,
                             UserRepository userRepository, KartRepository kartRepository,
                             List<RaceResultObserver> observers) {
        this.resultRepository = resultRepository;
        this.raceRepository = raceRepository;
        this.userRepository = userRepository;
        this.kartRepository = kartRepository;
        this.observers = observers;
    }

    /**
     * Processes an incoming race result and saves it to the database.
     * @param raceId - id of the race
     * @param racerId - id of the racer
     * @param kartId - id of the kart
     * @param placingStart - starting place of the racer
     * @param placingEnd - ending place of the racer
     * @param bestTimeMs - best single lap time of the racer
     * @return a message indicating the success of the operation
     */
    public String processIncomingResult(Long raceId, Long racerId, Long kartId, int placingStart, int placingEnd, Long bestTimeMs) {

        // Search for attributes necessary to exist
        Race race = raceRepository.findById(raceId)
                .orElseThrow(() -> new IllegalArgumentException("Race does not exist."));

        User user = userRepository.findById(racerId)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        Kart kart = kartRepository.findById(kartId)
                .orElseThrow(() -> new IllegalArgumentException("Kart does not exist."));

        // User has to be a racer
        if (!(user instanceof Racer racer)) {
            throw new IllegalArgumentException("User is not a racer.");
        }

        // Check for an identical result
        if (resultRepository.existsByRaceAndRacer(race, racer)) {
            return "Identical data already in DB. Result not saved.";
        }

        // Creates a new RaceResult instance and saves it to the database
        RaceResult newResult = new RaceResult(placingStart, placingEnd, bestTimeMs, false, racer, race, kart);
        resultRepository.save(newResult);

        // Notifies observers about the new result
        notifyObservers(newResult);

        return "Result saved successfully.";
    }

    /**
     * Notifies all observers about a new race result.
     * @param newResult - new race result instance
     */
    private void notifyObservers(RaceResult newResult) {
        for (RaceResultObserver observer : observers) {
            observer.onNewRaceResult(newResult);
        }
    }

    /**
     * Retrieves a list of results for a specific race.
     * @param raceId the unique identifier of the race
     * @return a list of RaceResultDto objects representing the results for the specified race
     */
    public List<RaceResultDto> getResultsForRace(Long raceId) {
        return resultRepository.findByRaceId(raceId).stream()
                .map(result -> new RaceResultDto(
                        result.getId(),
                        result.getRacer().getUsername(),
                        result.getKart().getNumber(),
                        result.getBestTime()
                ))
                .toList();
    }
}