package cz.zcu.kart_arena.model.state;

import cz.zcu.kart_arena.model.Race;

/**
 * State representing a finished race.
 * Such a race cannot be started, finished, or canceled.
 */
public class FinishedState implements RaceState {

    @Override
    public void start(Race race) {
        throw new IllegalStateException("Cannot start a race that has already finished.");
    }

    @Override
    public void finish(Race race) {
        throw new IllegalStateException("Cannot end a race that has already finished.");
    }

    @Override
    public void cancel(Race race) {
        throw new IllegalStateException("Cannot cancel a race that has already finished.");
    }

    @Override
    public String getStatusName() {
        return "FINISHED";
    }
}
