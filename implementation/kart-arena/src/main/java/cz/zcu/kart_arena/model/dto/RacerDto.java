package cz.zcu.kart_arena.model.dto;

/**
 * Data Transfer Object for racer details.
 * @param id - racer's id
 * @param name - racer's full name
 * @param username - racer's username
 * @param city - racer's city of residence
 * @param isRestricted - is the racer restricted?
 */
public record RacerDto(
        Long id,
        String name,
        String username,
        String city,
        boolean isRestricted
) {}
