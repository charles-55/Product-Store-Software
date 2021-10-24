package myStore;

import java.util.ArrayList;

/**
 * The StoreManager Class
 *
 * @author Dhruv Sannd - 101156588
 * @author Osamudiamen Nwoko - 101152520
 *
 * @version 4.0
 */
 class StoreManager {

    private Inventory inventory;
    private ShoppingCart cart;
    private int cartID;

    /**
     * The default constructor
     */
    public StoreManager(){
        inventory = new Inventory() {};
        cart = new ShoppingCart() {
            @Override
            public int getProductQuantity(int id) {
                return 0;
            }
        };
        cartID = 1;
    }

    /**
     * Returns the amount in stock of a particular product
     *
     * @param product   Product, product to check amount in stock of
     * @return      int, the amount of a product in stock
     */
    public int checkStock(Product product) {
        return inventory.getProductQuantity(product.getID());
    }

    /**
     * Processes the transaction of a shopping cart
     *
     * @return      double, total price of all products bought
     */
    public double processTransaction(){
        double total = 0.0;
        ArrayList<int[]> receipt = new ArrayList<>();
        for (int[] c : cart.getCart()) {
            total += inventory.getProduct(c[0]).getPrice() * c[1];
            receipt.add(c);
        }
        System.out.println("Here is your receipt:" +
                "\nCartID: " + cartID +
                "\nProduct | Quantity | Unit Price");
        if(receipt.size() == 0) {
            System.out.println("You did not purchase anything.");
        } else {
            for (int[] i : receipt) {
                System.out.println(inventory.getProduct(i[0]).getName() + " | " + i[1] + " | $" + inventory.getProduct(i[0]).getPrice());
            }
            System.out.println("Total Price: $" + total);
            resetCart();
        }

        return total;
    }

    /**
     * Returns the contents of the shopping cart back to the inventory when the user quits
     */
    public void returnToInventory() {
        for (int[] c : cart.getCart()) {
            inventory.addProductQuantity(c[0], c[1]);
        }
    }

    /**
     * Adds a certain quantity of a product to the shopping cart
     *
     * @param id    int, ID of the product to add
     * @param quantity      int, amount of the product to add
     */
    public void addToCart(int id, int quantity) {
        if(inventory.getProduct(id) != null) {
            if (checkStock(inventory.getProduct(id)) >= quantity) {
                cart.addProductQuantity(id, quantity);
                inventory.removeProductQuantity(id, quantity);
            } else {
                System.out.println("Product is not sufficient in the stock.");
            }
        }
    }

    /**
     * Removes a certain quantity of a product from the shopping cart
     *
     * @param id    int, ID of the product to remove
     * @param quantity      int, amount of the product to remove
     */
    public void removeFromCart(int id, int quantity) {
        boolean removedItem = false;
        for(int[] x : cart.getCart()){
            if(id == x[0]){
                if(quantity <= x[1]) {
                    cart.removeProductQuantity(id, quantity);
                    inventory.addProductQuantity(id, quantity);
                    removedItem = true;
                }
            }
        }
        if(!removedItem){
            System.out.println("The quantity of this product to remove is bigger than what is in the cart.");
        }
    }

    /**
     * Resets the cart when a user has processed their transaction
     */
    public void resetCart() {
        cart = new ShoppingCart() {
            @Override
            public int getProductQuantity(int id) {
                return 0;
            }
        };
        cartID += 1;
    }

    /**
     * Returns a new cartID on demand
     *
     * @return      int, new cartID
     */
    public int getNewCartID() {
        cartID += 1;
        return cartID;
    }

    /**
     * Returns the cartID of the current user
     *
     * @return  int, the cartID of the user
     */
    public int getCartID(){
        return cartID;
    }

    /**
     * Returns the inventory
     *
     * @return      Inventory, inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * A helper method for the junit testing.
     * Sets the value of the inventory to the given inventory.
     *
     * @param inventory     Inventory, the given inventory
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns a list of the products in the inventory
     *
     * @return      ArrayList<Product>, list of the product
     */
    public ArrayList<Product> getInventoryProducts() {
        return inventory.getProductList();
    }

    /**
     * Returns the shopping cart list of the current user
     *
     * @return      ArrayList<int[]>, the shopping cart list of the user
     */
    public ArrayList<int[]> getCart() {
        return cart.getCart();
    }
}