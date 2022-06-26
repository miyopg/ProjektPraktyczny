package pl.sda.project.shop.aplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import pl.sda.project.shop.extra.OilBrands;
import pl.sda.project.shop.model.Oils;

import java.math.BigDecimal;

@Slf4j
public class ShopApp {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("sdashop");
        entityManager = entityManagerFactory.createEntityManager();

        System.out.println("połączono");
        System.out.println("połączono");
        System.out.println("sprawdzamgita");
        System.out.println("na lapku działa na PC nie");
        System.out.println("na lapku działa na PC nie");
        System.out.println("na lapku asdasdasd nie");

        //showOils();
        testAddOil();
        showOils();

        entityManager.close();
        entityManagerFactory.close();
    }


    private static Oils addOil(OilBrands brand, String density, String capacity, BigDecimal price, Integer quantity) {
        var newOil = new Oils();
        newOil.setBrand(brand);
        newOil.setDensity(density);
        newOil.setCapacity(capacity);
        newOil.setPrice(price);
        newOil.setQuantity(quantity);
        return newOil;
    }

    private static void testAddOil() {
        Oils newOil = new Oils();
       /* newOil.setId(100);
        newOil.setBrand(OilBrands.ELF);
        newOil.setDensity("60W-60");
        newOil.setCapacity("10L");
        newOil.setPrice(BigDecimal.valueOf(450));
        newOil.setQuantity(1);*/
        newOil = addOil(OilBrands.ELF, "60W-60", "10L", BigDecimal.valueOf(450), 1);
        entityManager.getTransaction().begin();
        entityManager.persist(newOil);
        entityManager.getTransaction().commit();
    }

    private static void showOils() {
        String sql = """
                        select p from Oils p
                        """;

        var query = entityManager.createQuery(sql, Oils.class);
       // Oils singleResult = query.getSingleResult();
        var singleResult = query.getResultStream();
       // System.out.println(singleResult.count());
       singleResult.forEach(oils -> System.out.println(oils.getBrand()));




        //log.info("Max: {}", singleResult.getBrand());


    }
}
