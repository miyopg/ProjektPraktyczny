package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString


public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String street;
    protected String postCode;
    protected String city;



}
