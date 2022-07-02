package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static pl.sda.project.shop.aplication.ShopApp.*;

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

    public Basket(Long clientId, Integer oilId, Integer oilQuantity) {
        this.date = LocalDate.now();
        this.clientId = clientId;
        this.oilId = oilId;
        this.oilQuantity = oilQuantity;
    }
    @Override
    public String toString() {
        return "Basket{" +
                "id= " + id +
                ", date= " + date +
                ", client= " + getClientsById(clientId).getFirstName() +
                " " + getClientsById(clientId).getLastName() +
                ", oil= " + getOilsById(oilId).getBrand() +
                ", " + getOilsById(oilId).getDensity() +
                ", quantity=" + oilQuantity +
                '}';
    }
}
