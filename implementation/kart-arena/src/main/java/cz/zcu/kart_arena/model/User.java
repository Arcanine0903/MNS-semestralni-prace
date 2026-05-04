package cz.zcu.kart_arena.model;

import jakarta.persistence.*;

/**
 * User class representing a single user in the database.
 */
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // For testing purposes currently unhashed

    private boolean isRestricted = false;

    // Empty constructor required by JPA
    public User() {}

    /**
     * User class constructor
     * @param username - user's username
     * @param email - user's email
     * @param password - user's password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters
    public Long getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
    public boolean isRestricted(){
        return this.isRestricted;
    }

    // Business methods

    /**
     * Bans the user
     */
    public void ban() {
        this.isRestricted = true;
    }

    /**
     * Unbans the user
     */
    public void unban() {
        this.isRestricted = false;
    }
}