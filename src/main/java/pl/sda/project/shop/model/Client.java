package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
@Table(name = "clients")
@Entity
@Getter
@NoArgsConstructor
@ToString


public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "first_name", nullable = false, length = 32 )
    protected String firstName;
    @Column(name = "last_name", nullable = false, length = 32, insertable = false, updatable = false)
    protected String lastName;
    @Column(name = "email", nullable = false, length = 64, unique = true)
    protected String email;
    @Column(name = "phone_number", nullable = false, length = 9, unique = true)
    protected String phoneNumber;
    @Column(name = "last_name", nullable = false, length = 64 )
    protected String street;
    @Column(name = "post_code", nullable = false, length = 6 )
    protected String postCode;
    @Column(name = "city", nullable = false, length = 64 )
    protected String city;



}
