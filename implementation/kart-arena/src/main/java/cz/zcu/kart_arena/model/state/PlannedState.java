package cz.zcu.kart_arena.model.state;

import cz.zcu.kart_arena.model.Race;

/**
 * State representing a race that is planned but not yet started.
 */
public class PlannedState implements RaceState {

    @Override
    public void start(Race race) {
        race.setState(new OngoingState());
    }

    @Override
    public void finish(Race race) {
        throw new IllegalStateException("Cannot end a race that hasn't started yet.");
    }

    @Override
    public void cancel(Race race) {
        race.setState(new CanceledState());
    }

    @Override
    public String getStatusName() {
        return "PLANNED";
    }
}