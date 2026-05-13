package cz.zcu.kart_arena.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Employee class representing a staff member in the system.
 */
@Entity
@Table(name = "employee")
public class Employee extends User {

    @Column(nullable = false, unique = true)
    private String birthCertificateNumber;

    /**
     * Empty constructor required by JPA.
     */
    public Employee() {}


    /**
     * Constructor of the Employee class.
     * @param username - username of the employee (used for login)
     * @param password - password of the employee (used for login)
     * @param name - name of the employee
     * @param birthCertificateNumber - birth certificate number of the employee
     * @param city - city of residence of the employee
     * @param address - address of the employee
     * @param phoneNumber - phone number of the employee
     */
    public Employee(String username, String password, String name,
                    String birthCertificateNumber, String city, String address, String phoneNumber) {
        super(username, password, name, city, address, phoneNumber );
        this.birthCertificateNumber = birthCertificateNumber;
    }

    // Getters
    public String getBirthCertificateNumber() { return birthCertificateNumber; }

}