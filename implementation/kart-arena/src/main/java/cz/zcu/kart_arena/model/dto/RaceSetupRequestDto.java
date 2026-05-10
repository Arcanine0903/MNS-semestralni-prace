package cz.zcu.kart_arena.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for race setup request.
 * @param raceDate - date of the race
 * @param raceTime - time of the race
 * @param track - track where the race takes place
 */
public record RaceSetupRequestDto(
        LocalDate raceDate,
        LocalTime raceTime,
        String track
) {}