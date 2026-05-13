package cz.zcu.kart_arena.model.dto;

import java.time.LocalTime;

/**
 * DTO for race control setup.
 * @param raceId - id of the race
 * @param track - track where the race takes place
 * @param raceTime - time of the race
 */
public record RaceControlSetupDto(
        Long raceId,
        String track,
        LocalTime raceTime
) {}