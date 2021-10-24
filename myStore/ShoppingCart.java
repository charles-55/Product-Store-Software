package myStore;

import java.util.ArrayList;

/**
 * The ShoppingCart Class
 *
 * @author Dhruv Sannd - 101156588
 * @author Osamudiamen Nwoko - 101152520
 *
 * @version 2.0
 */
public abstract class ShoppingCart implements ProductStockContainer {
    private ArrayList<int[]> cart;

    /**
     * The default constructor
     */
    public ShoppingCart() {
        // initialize the cart
        cart = new ArrayList<int[]>();
    }

    /**
     * Adds a product to the shopping cart
     *
     * @param id        int, the product id
     * @param quantity  int, the quantity of a product to add
     */
    public void addProductQuantity(int id, int quantity) {
        boolean added = false;
        for(int[] x : cart) {
            if(x[0] == id) {
                x[1] += quantity;
                added = true;
            }
        }
        if(!added) {
            int[] a = {id, quantity};
            cart.add(a);
        }
    }

    /**
     * Removes a product from the shopping cart
     *
     * @param id        int, the product id
     * @param quantity  int, the quantity of a product to remove
     */
    public void removeProductQuantity(int id, int quantity) {
        for(int[] x : cart) {
            if(x[0] == id) {
                if(quantity <= x[1]) {
                    x[1] -= quantity;
                }
            }
        }
    }

    /**
     * Gets the cart arrayList
     *
     * @return      ArrayList<int[]>, the cart arrayList
     */
    public ArrayList<int[]> getCart(){
        return cart;
    }

    /**
     * Returns the number of different products in the cart
     *
     * @return      int, number of different products
     */
    public int getNumOfProducts() {
        int amount = 0;
        for(int[] i : cart) {
            if(i[1] > 0) {
                amount++;
            }
        }
        return amount;
    }
}
