package cz.zcu.kart_arena.model;

import jakarta.persistence.*;

/**
 * Kart class representing a single kart in the system.
 */
@Entity
@Table(name = "kart")
public class Kart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String color;

    @Column(name = "kart_number", nullable = false, unique = true)
    private int number;

    /**
     * Empty constructor required by JPA.
     */
    public Kart() {}


    /**
     * Kart class constructor.
     * @param color - color of the kart
     * @param number - number of the kart
     */
    public Kart(String color, int number) {
        this.color = color;
        this.number = number;
    }

    // Getters
    public Long getId() { return id; }
    public String getColor() { return color; }
    public int getNumber() { return number; }

}