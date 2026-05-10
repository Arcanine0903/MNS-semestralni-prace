package cz.zcu.kart_arena.model.dto;

import java.time.LocalTime;

/**
 * DTO for race.
 * @param id - id of the race
 * @param raceTime - time of the race
 */
public record RaceDto(
        Long id,
        LocalTime raceTime
) {}