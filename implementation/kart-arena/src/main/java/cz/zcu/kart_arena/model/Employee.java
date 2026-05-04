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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String birthCertificateNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    /**
     * Empty constructor required by JPA.
     */
    public Employee() {}


    /**
     * Constructor of the Employee class.
     * @param username - username of the employee (used for login)
     * @param password - password of the employee (used for login)
     * @param name - first name of the employee
     * @param surname - last name of the employee
     * @param birthCertificateNumber - birth certificate number of the employee
     * @param city - city of residence of the employee
     * @param address - address of the employee
     * @param phoneNumber - phone number of the employee
     */
    public Employee(String username, String password, String name, String surname,
                    String birthCertificateNumber, String city, String address, String phoneNumber) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.birthCertificateNumber = birthCertificateNumber;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getBirthCertificateNumber() { return birthCertificateNumber; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
}