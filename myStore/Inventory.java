package myStore;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Inventory Class
 *
 * @author Dhruv Sannd - 101156588
 * @author Osamudiamen Nwoko - 101152520
 *
 * @version 3.0
 */
public class Inventory implements ProductStockContainer {
    private ArrayList<Integer> quantity = new ArrayList<Integer>();
    private ArrayList<Product> products = new ArrayList<Product>();

    /**
     * The default constructor
     */
    public Inventory() {
        // initialize the inventory
        initialize();
    }

    /**
     * Adds a product to the inventory
     *
     * @param obj   Product, the product to add
     */
    public void addProduct(Product obj) {
        boolean addCheck = false;   // to check if the product has been added or not
        for(Product i : products) {
            if (i.getID() == obj.getID()) {     // if product is not new
                addCheck = true;
                break;
            }
        }
        if (!addCheck) {    // if product is new
            products.add(obj);
            quantity.add(1);    // sets the quantity of the newly added product to 1 by default
        }
    }

    /**
     * Get the stock amount of a particular product in the inventory
     *
     * @param id    int, The product id
     * @return      int, the stock amount in the inventory
     */
    public int getProductQuantity(int id) {
        int index = 0;
        for(Product i : products) {
            if(i.getID() == id) {     // if the ID exists in the Inventory
                return quantity.get(index);
            }
            index++;
        }
        return 0; // if the ID doesn't exist in the Inventory
    }

    /**
     * Adds to the stock of a product in the inventory.
     * Adds product to the inventory if it does not exist there.
     *
     * @param id    int, ID of the product
     * @param addValue      int, amount of stock to add
     */
    public void addProductQuantity(int id, int addValue) {
        int index = 0;
        boolean addCheck = false;   // to check if the stock has been added
        for(Product i : products) {
            if (i.getID() == id) {     // if product is not new
                quantity.set(index, quantity.get(index) + addValue);
                addCheck = true;
                break;
            }
            index++;
        }
        if (!addCheck) {    // if product is new
            System.out.println("A product with this ID does not exist in the inventory. Now creating a new one...");
            Product obj = newProduct();
            products.add(obj);
            quantity.add(addValue);
        }
    }

    /**
     * A helper method for the addStock method.
     * Creates a new product to be added.
     *
     * @return  Product, the newly created product
     */
    public Product newProduct() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the Product name: ");
        String name = input.next();
        System.out.print("\nEnter the Product ID: ");
        int id = input.nextInt();
        System.out.print("\nEnter the Product price: ");
        double price = input.nextDouble();
        System.out.print("\n");
        input.close();
        return new Product(name, id, price);
    }

    /**
     * Removes from the stock of a product in the inventory
     *
     * @param id    int, the ID of the product
     * @param removeValue   int, the quantity to remove
     */
    public void removeProductQuantity(int id, int removeValue) {
        int index = 0;
        boolean removeCheck = false;    //to check if stock has been removed
        int available = 0;
        for (Product i : products) {
            if(i.getID() == id) {
                if (quantity.get(index) >= removeValue) {     // if there is at least enough in stock
                    quantity.set(index, quantity.get(index) - removeValue);
                    removeCheck = true;
                } else {    // if there isn't enough
                    available = quantity.get(index);
                }
                break;
            }
            index++;
        }
        if (!removeCheck) {     // feedback if there isn't enough
            System.out.println("The quantity available of" + id + "is less than the required to remove.");
            System.out.println("The amount available is " + available + ".");
        }
    }

    /**
     * Returns a product with a given ID
     *
     * @param id    int, ID of the product
     * @return      Product, product with the given ID
     */
    public Product getProduct(int id) {
        for(Product i : products) {
            if(i.getID() == id) {   // returns the product with the particular ID
                return i;
            }
        }
        return null;    // returns null if there is no product with the given ID
    }

    /**
     * Returns a list of the products in the inventory
     *
     * @return      ArrayList<Product>, list of the product
     */
    public ArrayList<Product> getProductList() {
        return products;
    }

    /**
     * Returns the number of different products in the inventory
     *
     * @return      int, number of different products
     */
    public int getNumOfProducts() {
        int amount = 0;
        for(int i : quantity) {
            if(i > 0) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * A method for testing purposes
     */
    private void initialize() {
        products.add(new Product("Phone", 301, 800));
        quantity.add(20);
        products.add(new Product("Laptop", 302, 1200));
        quantity.add(20);
        products.add(new Product("TV", 303, 500));
        quantity.add(20);
        products.add(new Product("Notebook", 205, 10));
        quantity.add(100);
        products.add(new Product("Pen", 601, 5));
        quantity.add(150);
        products.add(new Product("Pencil", 602, 2.5));
        quantity.add(150);
        products.add(new Product("Eraser", 603, 2));
        quantity.add(150);
    }
}
