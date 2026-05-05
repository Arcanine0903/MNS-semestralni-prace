package cz.zcu.kart_arena.model;

import cz.zcu.kart_arena.model.state.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Race class representing a single race in the system.
 */
@Entity
@Table(name = "race")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate raceDate;

    @Column(nullable = false)
    private LocalTime raceTime;

    @Column(nullable = false)
    private String track;

    // Save the name of the status to the database
    @Column(nullable = false)
    private String statusName;

    // @Transient ensures that this field is not saved to the database
    @Transient
    private RaceState state;

    /**
     * Empty constructor required by JPA.
     */
    public Race() {}

    /**
     * Race class constructor.
     * @param raceDate - date of the race
     * @param raceTime - time of the race
     * @param track - track where the race takes place
     */
    public Race(LocalDate raceDate, LocalTime raceTime, String track) {
        this.raceDate = raceDate;
        this.raceTime = raceTime;
        this.track = track;

        // New race is planned by default
        this.setState(new PlannedState());
    }

    /**
     * Loads the state of the race based on the statusName.
     * Automatically called by Spring when loading a race from the database.
     */
    @PostLoad
    private void onLoad() {
        switch (this.statusName) {
            case "PLANNED" -> this.state = new PlannedState();
            case "ONGOING" -> this.state = new OngoingState();
            case "FINISHED" -> this.state = new FinishedState();
            case "CANCELLED" -> this.state = new CanceledState();
            default -> throw new IllegalStateException("Unknown state: " + statusName);
        }
    }

    // Getters
    public Long getId() { return id; }
    public LocalDate getRaceDate() { return raceDate; }
    public LocalTime getRaceTime() { return raceTime; }
    public String getTrack() { return track; }
    public String getStatusName() { return statusName; }

    /**
     * Sets the state of the race.
     * @param newState - new state of the race
     */
    public void setState(RaceState newState) {
        this.state = newState;
        this.statusName = newState.getStatusName();
    }


    // Methods for changing the state of the race.
    public void start() {
        this.state.start(this);
    }

    public void finish() {
        this.state.finish(this);
    }

    public void cancel() {
        this.state.cancel(this);
    }
}