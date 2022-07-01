package pl.sda.project.shop.aplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import pl.sda.project.shop.model.Client;
import pl.sda.project.shop.model.Oils;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        Client kowalskiJan = new Client("Jan","Kowalski","kowalski_jan99@poczta.pl",
                "123456789","Popiełuszki","31-300","Chrubieszów");
        Client kowalskiJan2 = new Client("Jan","Kowalski","kowalski_jan99@poczta@asdf.pl",
                "123456789","Popiełuszki","31-300","Chrubieszów");
        Client kowalskiJan3 = new Client("Jan","Kowalski","kowalski_jan99@poczta.pl",
                "155","Popiełuszki","31-300","Chrubieszów");


        showOils().forEach(System.out::println);
        System.out.println("++++++++++++++++++++++++++++");
        showClients().forEach(System.out::println);



        entityManager.close();
        entityManagerFactory.close();
    }


    private static void addOil(Oils oil) {
        entityManager.getTransaction().begin();
        entityManager.persist(oil);
        entityManager.getTransaction().commit();
    }

    private static void addClients(Client client) {
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }


    public static List<Oils> showOils() {
        List<Oils> resultList;
        return resultList = entityManager.createQuery("FROM Oils c", Oils.class)
                .getResultList();
    }

    public static List<Client> showClients() {
        List<Client> resultList;
        return resultList = entityManager.createQuery("FROM Client c", Client.class)
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
    
    public static void addClinetToDb(Client client) {
        List<Client> resultList;
        if (!emailAdressValidation(client.getEmail())){
            System.out.println("Niepoprawny email");
        }
        else if (!phoneNumberValidation(client.getPhoneNumber())){
            System.out.println("Niepoprawny nr telefonu");
        }
        else {
            resultList = entityManager.createQuery("FROM Client c Where c.email = :email And c.phoneNumber = :number", Client.class)
                    .setParameter("email", client.getEmail())
                    .setParameter("number", client.getPhoneNumber())
                    .getResultList();
            if (resultList.isEmpty()) {
                System.out.println("Dodano nowego Klienta");
                addClients(client);
            } else {
                System.out.println("Klient jest już w bazie danych");
            }
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

    public static void removeClientFromDbById(Integer id)throws Exception{
        try {
            Client client = entityManager.find(Client.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(client);
            entityManager.getTransaction().commit();
        }
        catch (Exception e){
            System.out.println("Brak klienta o takim id w bazie danych");
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

    public static boolean emailAdressValidation(String email) {
        boolean result;
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                ")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        result = matcher.find();
        return result;
    }

    public static boolean phoneNumberValidation(String phoneNumber) {
        boolean result;
        Pattern pattern = Pattern.compile("^[0-9]{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        result = matcher.find();
        return result;
    }

}

