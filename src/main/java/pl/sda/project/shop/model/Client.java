package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
@Table(name = "clients")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString


public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "first_name", nullable = false, length = 32 )
    protected String firstName;
    @Column(name = "last_name", nullable = false, length = 32)
    protected String lastName;
    @Column(name = "email", nullable = false, length = 64, unique = true)
    protected String email;
    @Column(name = "phone_number", nullable = false, length = 9, unique = true)
    protected String phoneNumber;
    @Column(name = "street", nullable = false, length = 64 )
    protected String street;
    @Column(name = "post_code", nullable = false, length = 6 )
    protected String postCode;
    @Column(name = "city", nullable = false, length = 64 )
    protected String city;

    public Client(String firstName, String lastName, String email, String phoneNumber, String street, String postCode, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.postCode = postCode;
        this.city = city;
    }
}
