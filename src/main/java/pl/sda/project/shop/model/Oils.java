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
@ToString

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
