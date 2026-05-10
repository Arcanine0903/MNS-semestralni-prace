package cz.zcu.kart_arena.model.dto;

/**
 * DTO for login request.
 * @param username username of the user
 * @param password password of the user
 */
public record LoginRequestDto(
        String username,
        String password
) {}
