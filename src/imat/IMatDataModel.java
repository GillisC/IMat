package imat;


import se.chalmers.cse.dat216.project.*;

import java.util.List;

public class IMatDataModel {
    private static IMatDataModel instance = null;
    private IMatDataHandler iMatDataHandler;

    /**
     * Does nothing
     */
    protected IMatDataModel() {
    }

    /**
     * Returns the singleton of IMatDataHandlerModel
     *
     * @return IMatDataHandlerModel
     */
    public static IMatDataModel getInstance() {
        if (instance == null) {
            instance = new IMatDataModel();
            instance.init();
        }
        return instance;
    }

    private void init() {
        iMatDataHandler = IMatDataHandler.getInstance();
    }

    /**
     * Saves stored data to text files in imat folder
     */
    public void shutdown() {
        iMatDataHandler.shutDown();
    }

    public Customer getCustomer() {
        return instance.getCustomer();
    }

    public User getUser() {
        return iMatDataHandler.getUser();
    }

    public CreditCard getCreditCard() {
        return iMatDataHandler.getCreditCard();
    }

    public ShoppingCart getShoppingCart() {
        return iMatDataHandler.getShoppingCart();
    }

    public Order placeOrder() {
        return iMatDataHandler.placeOrder();
    }

    public Order placeOrder(boolean clearShoppingCart) {
        return iMatDataHandler.placeOrder(clearShoppingCart);
    }

    public List<Order> getOrders() {
        return iMatDataHandler.getOrders();
    }

    public Product getProduct(int idNbr) {
        return iMatDataHandler.getProduct(idNbr);
    }

    public List<Product> getProducts() {
        return iMatDataHandler.getProducts();
    }

    public List<Product> getProducts(ProductCategory pc) {
        return iMatDataHandler.getProducts(pc);
    }

    public List<Product> findProducts(String s) {
        return iMatDataHandler.findProducts(s);
    }

    public void addProduct(Product p) {
        iMatDataHandler.addProduct(p);
    }

    public void removeProduct(Product p) {
        iMatDataHandler.removeProduct(p);
    }

    public void addFavorite(Product p) {
        iMatDataHandler.addFavorite(p);
    }

    public void removeFavorite(Product p) {
        iMatDataHandler.removeFavorite(p);
    }

}
