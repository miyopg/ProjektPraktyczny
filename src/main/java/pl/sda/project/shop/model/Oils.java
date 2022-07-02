package pl.sda.project.shop.model;

import jakarta.persistence.*;
import lombok.*;
import pl.sda.project.shop.extra.OilBrands;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Setter
@Entity
@Getter
@NoArgsConstructor

public class Oils {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Enumerated(EnumType.STRING)
    protected OilBrands brand;
    protected String density;
    protected String capacity;
    protected BigDecimal price;
    protected Integer quantity;

    public Oils(OilBrands brand, String density, String capacity, BigDecimal price, Integer quantity) {
        this.brand = OilBrands.valueOf(brand.getName());
        this.density = density;
        this.capacity = capacity;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Oils{" +
                "id=" + id +
                ", brand=" + brand.getName() +
                ", density='" + density + '\'' +
                ", capacity='" + capacity + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oils oils = (Oils) o;
        return Objects.equals(id, oils.id) && brand.getName() == oils.brand.getName() && Objects.equals(density, oils.density) && Objects.equals(capacity, oils.capacity) && Objects.equals(price, oils.price) && Objects.equals(quantity, oils.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, density, capacity, price, quantity);
    }
}
