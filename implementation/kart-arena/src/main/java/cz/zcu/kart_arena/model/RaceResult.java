package cz.zcu.kart_arena.model;

import jakarta.persistence.*;

/**
 * RaceResult class representing a singular result of a race.
 */
@Entity
@Table(name = "race_result")
public class RaceResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placing_start")
    private int placingStart;

    @Column(name = "placing_end")
    private int placingEnd;

    @Column(name = "best_time_ms")
    private Long bestTime;

    @Column(name = "paid_for")
    private boolean paidFor = false; // Currently a placeholder, paying out of scope

    // 1:N relation with the Racer class
    @ManyToOne
    @JoinColumn(name = "racer_id", nullable = false)
    private Racer racer;

    // 1:N relation with the Race class
    @ManyToOne
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    // 1:N relation with the Kart class
    @ManyToOne
    @JoinColumn(name = "kart_id", nullable = false)
    private Kart kart;

    /**
     * Empty constructor required by JPA.
     */
    public RaceResult() {}

    /**
     * RaceResult class constructor.
     * @param placingStart - starting place of the racer
     * @param placingEnd - ending place of the racer
     * @param bestTime - best single lap time of the racer
     * @param paidFor - has the racer paid for the race?
     * @param racer - racer who participated in the race
     * @param race - race where the racer participated
     * @param kart - kart used in the race
     */
    public RaceResult(int placingStart, int placingEnd, Long bestTime, boolean paidFor, Racer racer, Race race, Kart kart) {
        this.placingStart = placingStart;
        this.placingEnd = placingEnd;
        this.bestTime = bestTime;
        this.paidFor = paidFor;
        this.racer = racer;
        this.race = race;
        this.kart = kart;
    }

    // Getters
    public Long getId() { return id; }
    public int getPlacingStart() { return placingStart; }
    public int getPlacingEnd() { return placingEnd; }
    public Long getBestTime() { return bestTime; }
    public boolean isPaidFor() { return paidFor; }
    public Racer getRacer() { return racer; }
    public Race getRace() { return race; }
    public Kart getKart() { return kart; }

    /**
     * Placeholder method for a future payment system.
     */
    public void markAsPaid() {
        this.paidFor = true;
    }
}