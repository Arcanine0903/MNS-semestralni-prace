package cz.zcu.kart_arena.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * User class representing a single user in the database.
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    public User() {}

    public User(String username, String password, String name, String city, String address, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
}