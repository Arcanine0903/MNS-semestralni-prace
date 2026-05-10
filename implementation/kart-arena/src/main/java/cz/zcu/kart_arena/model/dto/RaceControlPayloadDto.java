package cz.zcu.kart_arena.model.dto;

/**
 * DTO for race control payload.
 * @param raceId - id of the race
 * @param racerId - id of the racer
 * @param kartId - id of the kart
 * @param placingStart - starting place of the racer
 * @param placingEnd - ending place of the racer
 * @param bestTime - best single lap time of the racer
 */
public record RaceControlPayloadDto(
        Long raceId,
        Long racerId,
        Long kartId,
        int placingStart,
        int placingEnd,
        Long bestTime
) {}

