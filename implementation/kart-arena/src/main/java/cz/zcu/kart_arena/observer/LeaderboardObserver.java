package cz.zcu.kart_arena.observer;

import cz.zcu.kart_arena.model.RaceResult;

import java.util.Observer;

public class LeaderboardObserver implements RaceResultObserver {

    @Override
    public void onNewRaceResult(RaceResult result) {
        System.out.println(">>> [OBSERVER - LEADERBOARD] Updating leaderboard. " +
                "Racer " + result.getRacer().getName() + " managed best time of " + result.getBestTime()/1000 + " seconds.");
    }
}
