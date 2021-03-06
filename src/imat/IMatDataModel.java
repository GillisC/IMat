package imat;


import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import se.chalmers.cse.dat216.project.*;

import java.util.*;

import static se.chalmers.cse.dat216.project.ProductCategory.CABBAGE;
import static se.chalmers.cse.dat216.project.ProductCategory.ROOT_VEGETABLE;

public class IMatDataModel {
    private static IMatDataModel instance = null;
    private IMatDataHandler iMatDataHandler;
    private static String selectedDay = null;
    protected String selectedTime = null;

    // Main Category maps to a list of products that belong in that category
    public final Map<String, List<ProductListItem>> mainCategoryMap = new HashMap<>();

    // Maps A main category to their respective subcategories
    public final Map<String, List<String>> subCategoryMap = new HashMap<>();

    public boolean selectTimeComplete;
    public boolean selectDeliveryComplete;
    public boolean selectPayComplete;

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
        selectTimeComplete = false;
        selectDeliveryComplete = false;
        selectPayComplete = false;
    }

    /**
     * Saves stored data to text files in imat folder
     */
    public void resetProgressIndicator() {
        selectTimeComplete = false;
        selectDeliveryComplete = false;
        selectPayComplete = false;
    }
    public String getSelectedDay() {
        return selectedDay;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedDay(String s) {
        selectedDay = s;
    }

    public void setSelectedTime(String s) {
        selectedTime = s;
    }

    public void shutdown() {
        iMatDataHandler.shutDown();
    }

    public Customer getCustomer() {
        return iMatDataHandler.getCustomer();
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

    public void placeOrder(boolean clearShoppingCart) {
        iMatDataHandler.placeOrder(clearShoppingCart);
        resetProgressIndicator();
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

    public double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /* Added Method. Checks if the product already is a shoppingItem, if yes increment amount otherwise make a new shopping item and add it to the cart */
    public void addToShoppingCart(Product product) {
        List<ShoppingItem> currShoppingItems = getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : currShoppingItems) {
            if (Objects.equals(shoppingItem.getProduct().getName(), product.getName())) {
                if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "kg")) {
                    shoppingItem.setAmount(shoppingItem.getAmount() + 0.1);
                } else {
                    shoppingItem.setAmount(shoppingItem.getAmount() + 1);
                }
                return;

            }
        }
        ShoppingItem shoppingItem = new ShoppingItem(product);
        if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "kg")) {
            shoppingItem.setAmount(0.1);
        } else {
            shoppingItem.setAmount(1);
        }
        getShoppingCart().addItem(shoppingItem);
    }

    public void manualAddToShoppingCart(Product product, int amount) {
        for (ShoppingItem item : getShoppingCart().getItems()) {
            if (Objects.equals(item.getProduct().getName(), product.getName())) {
                item.setAmount(amount);
                return;
            }
        }
        ShoppingItem shoppingItem = new ShoppingItem(product);
        shoppingItem.setAmount(amount);
        getShoppingCart().addItem(shoppingItem);
    }

    public void removeFromShoppingCart(Product product) {
        ShoppingCart shoppingCart = getShoppingCart();
        List<ShoppingItem> currShoppingItems = shoppingCart.getItems();
        for (ShoppingItem shoppingItem : currShoppingItems) {
            if (Objects.equals(shoppingItem.getProduct().getName(), product.getName())) {

                if (round(shoppingItem.getAmount(), 1) == 0.0) {
                    shoppingCart.removeItem(shoppingItem);
                    return;
                } else if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "kg")) {
                    shoppingItem.setAmount(shoppingItem.getAmount() - 0.1);
                } else {
                    shoppingItem.setAmount(shoppingItem.getAmount() - 1);
                }


            }
        }
    }

    public void shutDown() {
        iMatDataHandler.shutDown();
    }

    public boolean isCustomerComplete() {
        return iMatDataHandler.isCustomerComplete();
    }
    public boolean isSelectTimeComplete() {return (!Objects.equals(getSelectedDay(), "") && !Objects.equals(getSelectedTime(), ""));}
    public boolean isCreditCardComplete() {
        CreditCard creditCard = getCreditCard();
        return (!Objects.equals(creditCard.getCardNumber(), "") && !String.valueOf(creditCard.getValidYear()).equals("") && !String.valueOf(creditCard.getValidMonth()).equals("") && !Objects.equals(creditCard.getCardType(), ""));
    }

    public String getDeliveryTime() {
        return (getSelectedDay() +" "+ getSelectedTime());
    }

    public String getProductCategoryName(ProductCategory category) {
        return switch (category) {
            case CABBAGE ->"K??lv??xter";
            case HERB ->"??rter" ;
            case POD -> "Baljv??xter";
            case ROOT_VEGETABLE -> "Rotfrukter" ;
            case BERRY -> "B??r";
            case CITRUS_FRUIT -> "Citrus Frukter";
            case EXOTIC_FRUIT -> "Exotiska Frukter";
            case MELONS -> "Meloner";
            case FRUIT -> "Frukt";
            case FLOUR_SUGAR_SALT -> "Mj??l, Socker & Salt";
            case PASTA -> "Pasta";
            case POTATO_RICE -> "Potatis & Ris";
            case NUTS_AND_SEEDS -> "N??tter & Fr??n";
            case COLD_DRINKS -> "Kalla Drycker";
            case HOT_DRINKS -> "Varma Drycker";
            case MEAT -> "K??tt";
            case VEGETABLE_FRUIT -> "Gr??nsaker";
            case FISH -> "Fisk & Skaldjur";
            case DAIRIES -> "Mejeri";
            default -> null;
        };
    }

    public ProductCategory getProductCategory(String subCategory) {
        return switch (subCategory) {
            case "K??lv??xter" -> ProductCategory.CABBAGE;
            case "??rter" -> ProductCategory.HERB;
            case "Baljv??xter" -> ProductCategory.POD;
            case "Rotfrukter" -> ProductCategory.ROOT_VEGETABLE;
            case "B??r" -> ProductCategory.BERRY;
            case "Citrus Frukter" -> ProductCategory.CITRUS_FRUIT;
            case "Exotiska Frukter" -> ProductCategory.EXOTIC_FRUIT;
            case "Meloner" -> ProductCategory.MELONS;
            case "Frukt" -> ProductCategory.FRUIT;
            case "Mj??l, Socker & Salt" -> ProductCategory.FLOUR_SUGAR_SALT;
            case "Pasta" -> ProductCategory.PASTA;
            case "Potatis & Ris" -> ProductCategory.POTATO_RICE;
            case "N??tter & Fr??n" -> ProductCategory.NUTS_AND_SEEDS;
            case "Kalla Drycker" -> ProductCategory.COLD_DRINKS;
            case "Varma Drycker" -> ProductCategory.HOT_DRINKS;
            default -> null;
        };
    }

    /* Clears items that have an amount of zero in the cart when going from main -> select time */
    public void sweepShoppingCart() {
        for (ShoppingItem item : getShoppingCart().getItems()) {
            if (round(item.getTotal(), 1) == 0.0) {
                getShoppingCart().removeItem(item);
            }
        }
    }

    public List<ProductListItem> findMatchingProducts(String s) {
        String lowS = s.toLowerCase();
        List<ProductListItem> result = new ArrayList<>();
        for (ProductListItem p: mainCategoryMap.get("Allt")) {
            String productName = p.productNameLabel.getText().toLowerCase();
            if (productName.contains(lowS)) {
                result.add(p);
            }
        }
        return result;
    }

    public List<ProductListItem> findMatchingProducts(ProductCategory category) {
        List<ProductListItem> result = new ArrayList<>();
        for (ProductListItem p: mainCategoryMap.get("Allt")) {
            ProductCategory pCategory = p.getProductListItemCategory();
            if (pCategory == category) {
                result.add(p);
            }
        }
        return result;
    }

    public void setOnHover(Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.getScene().setCursor(Cursor.HAND);
            }
        });

        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }
}
