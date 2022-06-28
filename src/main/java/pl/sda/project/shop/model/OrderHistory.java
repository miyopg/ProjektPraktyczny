package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "order_history")

public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "order_date", nullable = false)
    protected LocalDate date;
    @ManyToOne

    @javax.persistence.OneToOne
    @JoinColumn(name = "basket_id")
    protected Basket basket;


}
