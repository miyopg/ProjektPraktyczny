package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor

@Table(name = "basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected LocalDate date;
    protected Long clientId;
    protected Integer oilId;
    protected Integer oilQuantity;

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", date=" + date +
                ", clientId=" + clientId +
                ", oilId=" + oilId +
                ", oilQuantity=" + oilQuantity +
                '}';
    }
}
