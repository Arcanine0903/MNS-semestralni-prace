package cz.zcu.kart_arena.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * User class representing a single user in the database.
 */
@Entity
@Table(name = "racer")
public class Racer extends User{

    private boolean isRestricted = false;

    @Column(nullable = false)
    private LocalDate birthday;

    // Empty constructor required by JPA
    public Racer() {}

    /**
     * Racer class constructor
     * @param name - racer's full name
     * @param username - racer's username
     * @param password - racer's password
     * @param birthday - racer's birthday
     * @param city - racer's city of residence
     * @param address - racer's address
     * @param phoneNumber - racer's phone number
     */
    public Racer(String name, String username, String password, LocalDate birthday, String city, String address, String phoneNumber) {
        super(username, password, name, city, address, phoneNumber);
        this.birthday = birthday;
    }

    // Getters
    public boolean isRestricted() { return isRestricted; }
    public LocalDate getBirthday() { return birthday; }

    // Business methods
    /**
     * Bans the racer
     */
    public void ban() {
        this.isRestricted = true;
    }

    /**
     * Unbans the racer
     */
    public void unban() {
        this.isRestricted = false;
    }
}