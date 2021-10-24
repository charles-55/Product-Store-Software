package myStore;

/**
 * The ProductStockContainer Interface
 *
 * @author Dhruv Sannd - 101156588
 * @author Osamudiamen Nwoko - 101152520
 *
 * @version 1.0
 * @apiNote Interface uses Product id instead of Product for parameters.
 */
public interface ProductStockContainer {

    int getProductQuantity(int id);

    void addProductQuantity(int id, int amount);

    void removeProductQuantity(int id, int amount);

    int getNumOfProducts();
}
