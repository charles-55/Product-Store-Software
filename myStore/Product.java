package myStore;

/**
 * The Product Class
 *
 * @author Dhruv Sannd - 101156588
 * @author Osamudiamen Nwoko - 101152520
 * @version 2.0
 */
public class Product {
    private String name;
    private int id;
    private double price;

    public Product(String name, int id, double price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getName() {
        return  this.name;
    }

    public int getID() {
        return  this.id;
    }

    public double getPrice() {
        return  this.price;
    }
}
