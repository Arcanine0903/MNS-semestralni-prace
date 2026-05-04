package cz.zcu.kart_arena.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * User class representing a single user in the database.
 */
@Entity
@Table(name = "racer")
public class Racer extends User{

    @Column(nullable = false)
    private String name;

    private LocalDate birthday;
    private String city;
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    private boolean isRestricted = false;

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
        super(username, password);
        this.name = name;
        this.birthday = birthday;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getName() { return name; }
    public LocalDate getBirthday() { return birthday; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public boolean isRestricted() { return isRestricted; }

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