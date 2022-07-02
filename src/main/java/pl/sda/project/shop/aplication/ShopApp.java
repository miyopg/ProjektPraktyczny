package pl.sda.project.shop.aplication;

import jakarta.persistence.Convert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import pl.sda.project.shop.extra.OilBrands;
import pl.sda.project.shop.model.Basket;
import pl.sda.project.shop.model.Client;
import pl.sda.project.shop.model.Oils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static pl.sda.project.shop.extra.OilBrands.*;

@Slf4j
public class ShopApp {


    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    static boolean loggedIn = false;
    private static Client loggedClient;
    private static int menu;

    public static void main(String[] args) throws Exception {


        entityManagerFactory = Persistence.createEntityManagerFactory("sdashop");
        entityManager = entityManagerFactory.createEntityManager();

        System.out.println("połączono");
        Basket basket = new Basket();

        System.out.println(showOilsById(1));

        //simpleMenu();

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void loggingInMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nr telefonu:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Podaj email:");
        String email = scanner.nextLine();
        loggingIn(phoneNumber, email);
        System.out.println("1. Powrót");
        String ret = scanner.nextLine();
    }

    private static void addOil(Oils oil) {
        entityManager.getTransaction().begin();
        entityManager.persist(oil);
        entityManager.getTransaction().commit();
    }

    private static void addBasket(Basket basket) {
        entityManager.getTransaction().begin();
        entityManager.persist(basket);
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

    public static List<Client> showClientsById(long id) {
        List<Client> resultList;
        return resultList = entityManager.createQuery("FROM Client c Where c.id = :id", Client.class)
                .setParameter("id", id)
                .getResultList();
    }
    public static List<String> showOilsById(Integer id) {
        List<String> resultList;
        return resultList = entityManager.createQuery("FROM Oils c Where c.id = :id", String.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .collect(Collectors.toList());

    }

    public static List<Oils> showOilsByDensity(String density) {
        List<Oils> resultList;
        return resultList = entityManager.createQuery("FROM Oils c Where c.density = :density", Oils.class)
                .setParameter("density", density)
                .getResultList();
    }

    public static List<Oils> showOilsByName(String name) {
        List<Oils> resultList;

        return resultList = entityManager.createQuery("FROM Oils c Where c.brand = :name", Oils.class)
                .setParameter("name", name)
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
        if (resultList.isEmpty()) {
            System.out.println("Dodano nowy olej");
            addOil(oil);
        } else {
            System.out.println("Olej już istnieje w bazie danych");
        }
    }

    public static void addClientToDb(Client client) {
        List<Client> resultList;
        if (!emailAdressValidation(client.getEmail())) {
            System.out.println("Niepoprawny email");
        } else if (!phoneNumberValidation(client.getPhoneNumber())) {
            System.out.println("Niepoprawny nr telefonu");
        } else {
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

    public static void removeOilFromDbById(Integer id) throws Exception {
        try {
            Oils oil = entityManager.find(Oils.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(oil);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Brak oleju o takim id w bazie danych");
        }
    }

    public static void removeClientFromDbById(Integer id) throws Exception {
        try {
            Client client = entityManager.find(Client.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(client);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Brak klienta o takim id w bazie danych");
        }
    }

    public static void changeOliQuantityById(Integer id, Integer quantity) {
        try {
            Oils oil = entityManager.find(Oils.class, id);
            oil.setQuantity(quantity);
            entityManager.getTransaction().begin();
            entityManager.merge(oil);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
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

    public static void simpleMenu() {
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Sda Sklep");
            System.out.println("1. Pokaż całą ofertę oleji");
            System.out.println("2. Wyszukaj Olej");
            System.out.println("3. Zarejestruj Klienta");
            System.out.println("4. Zaloguj Klienta");
            System.out.println("5. Pokaż listę klientów");
            System.out.println("6. Pokaż zalogowanego klientów");
            System.out.println("7. Wyloguj klienta");
            System.out.println("8. Dodaj olej do koszyka");
            System.out.println("9. Pokaż koszyk klienta");
            System.out.println("10. Podsumuj koszyk klienta");
            System.out.println("11. Admin");
            System.out.println("12. Zakończ");
            menu = scanner.nextInt();
            switch (menu) {
                case 1:
                    showAllOilsMenu();
                    break;
                case 2:
                    showOilsMenu();
                    break;
                case 3:
                    addingClientMenu();
                    break;
                case 4:
                    loggingInMenu();
                    break;
                case 5:
                    showAllClientsMenu();
                    break;
                case 6:
                    showLogedClientMenu();
                    break;
                case 7:
                    logOut();
                    break;
                case 8:
                    System.out.println("work in progress");
                    break;
                case 9:
                    System.out.println("work in progress");
                    break;
                case 10:
                    System.out.println("work in progress");
                    break;
                case 11:
                    adminMenu();
                    break;
                default:
                    System.out.println("Do zobaczenia");
                    break;
            }
        } while (menu != 12);
    }

    public static void adminMenu() {
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Admin menu");
            System.out.println("1. Pokaż całą ofertę oleji");
            System.out.println("2. Wyszukaj Olej");
            System.out.println("3. Dodaj olej do bazy danych");
            System.out.println("4. Usuń olej z bazy danych");
            System.out.println("5. Aktualizuj ceny");
            System.out.println("6. Aktualizuj stany");
            System.out.println("7. Pokaż listę klientów");
            System.out.println("8. Usuń klienta z bazy danych");
            System.out.println("9. Zakończ");
            menu = scanner.nextInt();
            switch (menu) {
                case 1:
                    showAllOilsMenu();
                    break;
                case 2:
                    System.out.println("work in progress");
                    break;
                case 3:
                    System.out.println("work in progress");
                    break;
                case 4:
                    System.out.println("work in progress");
                    break;
                case 5:
                    System.out.println("work in progress");
                    break;
                case 6:
                    System.out.println("work in progress");
                    break;
                case 7:
                    System.out.println("work in progress");
                    break;
                case 8:
                    System.out.println("work in progress");
                    break;

                default:
                    System.out.println("Do zobaczenia");
                    break;
            }
        } while (menu != 9);
    }

    public static void addingClientMenu() {
        Scanner scanner = new Scanner(System.in);
        Client client = new Client();
        System.out.println("Podaj imie:");
        String name = scanner.nextLine();
        client.setFirstName(name);
        System.out.println("Podaj nazwisko:");
        String secondName = scanner.nextLine();
        client.setLastName(secondName);
        System.out.println("Podaj email:");
        String email = scanner.nextLine();
        client.setEmail(email);
        System.out.println("Podaj numer telefony:");
        String phoneNumber = scanner.nextLine();
        client.setPhoneNumber(phoneNumber);
        System.out.println("Podaj miasto:");
        String city = scanner.nextLine();
        client.setCity(city);
        System.out.println("Podaj ulicę:");
        String street = scanner.nextLine();
        client.setStreet(street);
        System.out.println("Podaj kod pocztowy:");
        String postCode = scanner.nextLine();
        client.setPostCode(street);
        addClientToDb(client);
        System.out.println("1. Powrót");
        String ret = scanner.nextLine();
    }

    public static void showOilsMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Po nazwie");
        System.out.println("2. Po gęstości");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            System.out.println("work in progress");
            System.out.println("1. Powrót");
            String ret = scanner.nextLine();

        } else {
            System.out.println("Podaj gęstość");
            String density = scanner.nextLine();
            if (showOilsByDensity(density).isEmpty()){
                System.out.println("Brak podanego oleju w bazie danych");
            }else {
                showOilsByDensity(density).forEach(System.out::println);
            }
            System.out.println("1. Powrót");
            String ret = scanner.nextLine();
        }
    }

    public static void loggingIn(String phoneNumber, String email) {
        Optional<Client> client = null;
        client = Optional.ofNullable(entityManager.createQuery("FROM Client c Where c.email = :email And c.phoneNumber = :number", Client.class)
                .setParameter("email", email)
                .setParameter("number", phoneNumber)
                .getSingleResult());
        if (client.isPresent()) {
            loggedIn = true;
            loggedClient = client.get();
            System.out.println("Zalogowano");
        } else {
            System.out.println("Niepoprawne dane");
        }
    }

    public static void showLogedClientMenu() {
        Scanner scanner = new Scanner(System.in);
        if (loggedIn) {
            showClientsById(loggedClient.getId()).forEach(System.out::println);
        } else {
            System.out.println("Brak zalogowanego klienta");
        }
        System.out.println("1. Powrót");
        String ret = scanner.nextLine();
    }

    public static void logOut() {
        Scanner scanner = new Scanner(System.in);
        loggedIn = false;
        System.out.println("Wylogowano");
        System.out.println("1. Powrót");
        String ret = scanner.nextLine();
    }

    public static void showAllOilsMenu(){
        Scanner scanner = new Scanner(System.in);
        showOils().forEach(System.out::println);
        System.out.println("1. Powrót");
        String ret = scanner.nextLine();
    }

    public static void showAllClientsMenu(){
        Scanner scanner = new Scanner(System.in);
        showClients().forEach(System.out::println);
        System.out.println("1. Powrót");
        String ret = scanner.nextLine();
    }

}

