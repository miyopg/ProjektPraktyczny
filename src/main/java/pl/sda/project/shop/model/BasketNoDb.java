package pl.sda.project.shop.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@ToString
@Setter

public class BasketNoDb{




    protected Integer id;

    protected LocalDate date;

    protected Integer clientId;

    protected Integer oilId;

    private Set<Oils> oilList;



    public boolean addOilToBasket(Oils oil) {
        return oilList.add(oil);
    }

    public Integer addQuantityOil(Integer quantityOil) {
        return quantityOil;
    }

    public Integer addClientToBasket(Client client) {
        clientId = client.getId();
        return clientId;
    }

    public LocalDate addDate() {
        date = LocalDate.now();
        return date;
    }

    public BasketNoDb (Integer id, Integer clientId) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.oilList = new HashSet<>();
    }

    public void printBasketNoDbInfo() {

        System.out.println("Basket id: " + id + ", Date: " + date + ", Client id: " + clientId + ", Oils List: ");
        oilList.stream().forEach(System.out::println);


    }

}
