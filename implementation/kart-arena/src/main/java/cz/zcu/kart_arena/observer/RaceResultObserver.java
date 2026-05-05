package cz.zcu.kart_arena.observer;

import cz.zcu.kart_arena.model.RaceResult;

/**
 * Observer interface for receiving race results.
 */
public interface RaceResultObserver {

    /**
     * Method called when a new race result is available.
     * @param result - race result object
     */
    void onNewRaceResult(RaceResult result);
}