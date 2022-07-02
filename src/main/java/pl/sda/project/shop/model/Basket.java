package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Table(name = "basket")

public class Basket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "order_date", nullable = false)
    protected LocalDate date;
    @Column(name = "client_id")
    protected Integer clientId;
    @Column(name = "oil_id")
    protected Integer oilId;
    @Column(name = "oil_quantity")
    private Integer oilQuantity;

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
/*

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "order_date", nullable = false)
    protected LocalDate date;
    @Column(name = "client_id")
    protected Integer clientId;
    @Column(name = "oil_id")
    protected Integer oilId;
    @Column(name = "oil_quantity")
    private Integer oilQuantity;



    public Integer addOilIdToOrder(Oils oil) {
        oilId = oil.getId();
        return oilId;
    }

    public Integer addClientToBasket(Integer clientId) {
       return clientId;
    }

    public LocalDate addDate() {
        date = LocalDate.now();
        return date;
    }


    public Basket(Integer id, LocalDate date, Integer clientId, Integer oilId, Integer oilQuantity) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.oilId = oilId;
        this.oilQuantity = oilQuantity;
    }

   */
/* public void printBasketInfo() {

        System.out.println("Basket id: " + id + ", Date: " + date + ", Client id: " + clientId + ", Oils id: " + oilId + "(quantity : " + oilQuantity + ")");
    } */


