package imat;


import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.util.List;
import java.util.Objects;

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
    public Image getFXImage(Product p) {
        return iMatDataHandler.getFXImage(p);
    }

    public Image getFXImage(Product p, double width, double height) {
        return iMatDataHandler.getFXImage(p, width, height);
    }

    public Image getFXImage(Product p, double requestedWidth, double requestedHeight, boolean preserveRatio) {
        return iMatDataHandler.getFXImage(p, requestedWidth, requestedHeight, preserveRatio);
    }
    public Image getImageFromUrl(String url) {
        return new Image(url);
    }

    public double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /* Added Method. Checks if the product already is a shoppingItem, if yes increment amount otherwise make a new shopping item and add it to the cart */
    public void addToShoppingCart(Product product) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        List<ShoppingItem> currShoppingItems = shoppingCart.getItems();
        for (ShoppingItem shoppingItem : currShoppingItems) {
            if (Objects.equals(shoppingItem.getProduct().getName(), product.getName())) {
                if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "st") || Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "förp")) {
                    shoppingItem.setAmount(shoppingItem.getAmount() + 1);
                } else {
                    shoppingItem.setAmount(shoppingItem.getAmount() + 0.1);
                }
                return;

            }
        }
    ShoppingItem shoppingItem = new ShoppingItem(product);
    if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "st") || Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "förp")) {
        shoppingItem.setAmount(1);
    } else {
        shoppingItem.setAmount(0.1);
    }
    IMatDataModel.getInstance().getShoppingCart().addItem(shoppingItem);
    }

    public void removeFromShoppingCart(Product product) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        List<ShoppingItem> currShoppingItems = shoppingCart.getItems();
        for (ShoppingItem shoppingItem : currShoppingItems) {
            if (Objects.equals(shoppingItem.getProduct().getName(), product.getName())) {

                if (shoppingItem.getAmount() == 0) {
                    shoppingCart.removeItem(shoppingItem);
                    return;
                }
                else if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "st") || Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "förp")) {
                    shoppingItem.setAmount(shoppingItem.getAmount() - 1);
                }
                else {
                    shoppingItem.setAmount(shoppingItem.getAmount() - 0.1);
                }


            }
        }
    }

    public String removeSuffixes(String s) {
        s = s.replace("kg", "");
        s = s.replace("st", "");
        s = s.replace("förp", "");
        return s;
    }

    public void incrementTextField(TextField textField, Product product) {
        String currentVal = textField.getText();
        currentVal = removeSuffixes(currentVal);
        double value = Double.parseDouble(currentVal);
        String suffix = product.getUnitSuffix();
        if (value == 0) {
            return;
        }
        if (Objects.equals(suffix, "kg")) {
            value -= 0.1;
            textField.setText(round(value, 2) + "kg");
        } else {
            value -= 1;
            textField.setText((int) value + "st");
        }
    }
}
