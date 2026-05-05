package cz.zcu.kart_arena.model.state;

import cz.zcu.kart_arena.model.Race;

/**
 * State representing a canceled race.
 * Such a race cannot be started, finished, or finished.
 */
public class CanceledState implements RaceState {

    @Override
    public void start(Race race) {
        throw new IllegalStateException("Cannot start a race that has been canceled.");
    }

    @Override
    public void finish(Race race) {
        throw new IllegalStateException("Cannot end a race that has been canceled.");
    }

    @Override
    public void cancel(Race race) {
        throw new IllegalStateException("Race already cancelled.");
    }

    @Override
    public String getStatusName() {
        return "CANCELLED";
    }
}
