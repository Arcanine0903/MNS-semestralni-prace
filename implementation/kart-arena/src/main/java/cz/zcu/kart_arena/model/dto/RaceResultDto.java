package cz.zcu.kart_arena.model.dto;

/**
 * DTO for a race result.
 * @param id - id of the race result
 * @param username - username of the racer
 * @param kartNumber - number of the kart used in the race
 * @param bestTimeMs - best time in milliseconds
 */
public record RaceResultDto(
        Long id,
        String username,
        int kartNumber,
        Long bestTimeMs
) {}


