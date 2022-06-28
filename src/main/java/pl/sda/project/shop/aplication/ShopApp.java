package pl.sda.project.shop.aplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import pl.sda.project.shop.model.Oils;

import java.math.BigDecimal;
import java.util.List;


import static pl.sda.project.shop.extra.OilBrands.MOTUL;

@Slf4j
public class ShopApp {


    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args) throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("sdashop");
        entityManager = entityManagerFactory.createEntityManager();

        System.out.println("połączono");
        Oils oilMotul1 = new Oils();
        oilMotul1.setBrand(MOTUL);
        oilMotul1.setCapacity("5");
        oilMotul1.setDensity("5w30");
        oilMotul1.setPrice(BigDecimal.valueOf(110));
        oilMotul1.setQuantity(1);
        Oils oilMotul2 = new Oils();
        oilMotul2.setBrand(MOTUL);
        oilMotul2.setCapacity("1");
        oilMotul2.setDensity("5w40");
        oilMotul2.setPrice(BigDecimal.valueOf(90));
        oilMotul2.setQuantity(15);

        showOils().forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++++");
        showOilsByDensity("5w40").forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++++");
        //addOilToDb(oilMotul2);
        System.out.println("++++++++++++++++++++++++++++");
        //changeOliQuantityById(1,200);
        System.out.println("++++++++++++++++++++++++++++");
        showOils().forEach(System.out::println);

        entityManager.close();
        entityManagerFactory.close();
    }


    private static void addOil(Oils oil) {
        entityManager.getTransaction().begin();
        entityManager.persist(oil);
        entityManager.getTransaction().commit();
    }

    public static List<Oils> showOils() {
        List<Oils> resultList;
        return resultList = entityManager.createQuery("FROM Oils c", Oils.class)
                .getResultList();
    }

    public static List<Oils> showOilsByDensity(String density) {
        List<Oils> resultList;
        return resultList = entityManager.createQuery("FROM Oils c Where c.density = :density", Oils.class)
                .setParameter("density", density)
                .getResultList();
    }
    //dodaje olej do bazy danych jeśli jeszcze takiego nie ma więc nie dublujemy
    public static void addOilToDb(Oils oil) {
        List<Oils> resultList;
         resultList = entityManager.createQuery("FROM Oils c Where c.density = :density And c.capacity = :capacity And c.brand = :name", Oils.class)
                .setParameter("density", oil.getDensity())
                .setParameter("capacity", oil.getCapacity())
                .setParameter("name", oil.getBrand())
                .getResultList();
        if (resultList.isEmpty()){
            System.out.println("Dodano nowy olej");
            addOil(oil);
        }
        else {
            System.out.println("Olej już istnieje w bazie danych");
        }
    }

    public static void removeOilFromDbById(Integer id)throws Exception{
        try {
            Oils oil = entityManager.find(Oils.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(oil);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            System.out.println("Brak oleju o takim id w bazie danych");
        }
    }

    public static void changeOliQuantityById(Integer id, Integer quantity){
        try {
            Oils oil = entityManager.find(Oils.class, id);
            oil.setQuantity(quantity);
            entityManager.getTransaction().begin();
            entityManager.merge(oil);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            System.out.println("Brak oleju o takim id w bazie danych");
        }

    }
}

