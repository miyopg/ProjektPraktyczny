package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Setter
@Table(name = "basket")

public class Basket{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "order_date", nullable = false)
    protected LocalDate date;
    @Column(name = "client_id")
    protected Integer clientId;
    @Column(name = "oil_id")
    protected Integer oilId;
    @Column(name = "oil_list")
    private String oilList;



    public Integer addOilToBasket(Oils oil) {
        oilId = oil.getId();
        return oilId;
    }

    public Integer addClientToBasket(Client client) {
        clientId = client.getId();
        return clientId;
    }

    public LocalDate addDate() {
        date = LocalDate.now();
        return date;
    }

    public Basket(Long id, LocalDate date, Integer clientId, String oilList, Oils oil) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.oilList = oilList;
    }

    public void printBasketInfo() {
        List<Oils> oilsList = new ArrayList<>();
        System.out.println("Basket id: " + id + ", Date: " + date + ", Client id: " + clientId + ", Oils List: ");
    }

}
