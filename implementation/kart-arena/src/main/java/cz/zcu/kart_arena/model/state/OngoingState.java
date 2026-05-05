package cz.zcu.kart_arena.model.state;

import cz.zcu.kart_arena.model.Race;

/**
 * An ongoing race state.
 * Such race cannot be canceled or started again.
 */
public class OngoingState implements RaceState {

    @Override
    public void start(Race race) {
        throw new IllegalStateException("Race already started.");
    }

    @Override
    public void finish(Race race) {
        race.setState(new FinishedState());
    }

    @Override
    public void cancel(Race race) {
        race.setState(new CanceledState());
    }

    @Override
    public String getStatusName() {
        return "ONGOING";
    }
}