package pl.sda.project.shop.aplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import pl.sda.project.shop.extra.OilBrands;
import pl.sda.project.shop.model.Oils;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.sql.SQLException;

@Slf4j
public class ShopApp {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args) throws SQLException {
        entityManagerFactory = Persistence.createEntityManagerFactory("sdashop");
        entityManager = entityManagerFactory.createEntityManager();

        System.out.println("połączono");


        //showOils();
       // testAddOil();
        showOils();
        deleteOilById(15);
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

    private static void deleteOilById (int id) throws SQLException {
        entityManager.getTransaction().begin();

        var query = entityManager.createQuery("select p from Oils p where p.id = :id", Oils.class);
        query.setParameter("id", id);

        try {
            var oilID = query.getSingleResult();
            entityManager.remove(oilID);
            entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            log.warn("Cannot delete non-existing Oil");
        }
    }

    private static void testAddOil() {
        Oils newOil = new Oils();
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
       singleResult.forEach(oils -> System.out.println(oils.getBrand() + " " + oils.getDensity()));




        //log.info("Max: {}", singleResult.getBrand());


    }
}
