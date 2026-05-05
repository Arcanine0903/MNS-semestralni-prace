package cz.zcu.kart_arena.model.state;

import cz.zcu.kart_arena.model.Race;

/**
 * Interface for representing the state of a race.
 */
public interface RaceState {
    void start(Race race);
    void finish(Race race);
    void cancel(Race race);
    String getStatusName();
}