package cz.zcu.kart_arena.model.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for registration request.
 * @param name - racer's full name
 * @param username - racer's chosen username
 * @param password - racer's chosen password
 * @param birthday - racer's birthday
 * @param city - racer's city of residence
 * @param address - racer's address
 * @param phoneNumber - racer's phone number
 */
public record RegistrationRequestDto(
        String name,
        String username,
        String password,
        LocalDate birthday,
        String city,
        String address,
        String phoneNumber
) {}