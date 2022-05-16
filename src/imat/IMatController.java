package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {

    private IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    private Map<String, ProductListItem> productListItemMap = new HashMap<>();

    // Main Category maps to a list of products that belong in that category
    private Map<String, List<ProductListItem>> mainCategoryMap = new HashMap<>();


    @FXML private ImageView shoppingCartImageView;
    @FXML private ImageView profileImageView;
    @FXML private TextField searchTextField;
    @FXML private Button searchButton;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private FlowPane productFlowPane;
    @FXML private FlowPane subCategoryFlowPane;
    @FXML private ScrollPane mainCategoryScrollPane;

    private void populateMainCategoryMap() {
        String[] categoryArray = {"Allt", "Grönsaker", "Frukt", "Mejeri", "Bröd", "Kött", "Fisk", "Skafferi", "Drickor"};

        // Adds products to "Allt" category
        List<Product> tempEverything = iMatDataModel.getProducts();
        List<ProductListItem> everything = new ArrayList<>();
        for (Product p: tempEverything) {
            everything.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Allt", everything);

        // Adds products to "Grönsaker" Category
        List<Product> tempGreens = new ArrayList<>();
        tempGreens.addAll(iMatDataModel.getProducts(ProductCategory.CABBAGE));
        tempGreens.addAll(iMatDataModel.getProducts(ProductCategory.HERB));
        tempGreens.addAll(iMatDataModel.getProducts(ProductCategory.POD));
        tempGreens.addAll(iMatDataModel.getProducts(ProductCategory.ROOT_VEGETABLE));

        List<ProductListItem> greens = new ArrayList<>();
        for (Product p: tempGreens) {
            greens.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Grönsaker", greens);

        // Adds products to "Frukt" Category
        List<Product> tempFruit = new ArrayList<>();
        tempFruit.addAll(iMatDataModel.getProducts(ProductCategory.BERRY));
        tempFruit.addAll(iMatDataModel.getProducts(ProductCategory.CITRUS_FRUIT));
        tempFruit.addAll(iMatDataModel.getProducts(ProductCategory.EXOTIC_FRUIT));
        tempFruit.addAll(iMatDataModel.getProducts(ProductCategory.MELONS));
        tempFruit.addAll(iMatDataModel.getProducts(ProductCategory.FRUIT));

        List<ProductListItem> fruit = new ArrayList<>();
        for (Product p: tempFruit) {
            fruit.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Frukt", fruit);

        // Adds products to "Mejeri" Category
        List<Product> tempDairy = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.DAIRIES));

        List<ProductListItem> dairy = new ArrayList<>();
        for (Product p: tempDairy) {
            dairy.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Mejeri", dairy);

        // Adds products to "Bröd" Category
        List<Product> tempBread = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.BREAD));

        List<ProductListItem> bread = new ArrayList<>();
        for (Product p: tempBread) {
            bread.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Bröd", bread);

        // Adds products to "Kött" Category
        List<Product> tempMeat = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.MEAT));

        List<ProductListItem> meat = new ArrayList<>();
        for (Product p: tempMeat) {
            meat.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Kött", meat);

        // Adds products to "Fisk" Category
        List<Product> tempFish = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.FISH));

        List<ProductListItem> fish = new ArrayList<>();
        for (Product p: tempFish) {
            fish.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Fisk", fish);

        // Adds products to "Skafferi" Category
        List<Product> tempPantry = new ArrayList<>();
        tempPantry.addAll(iMatDataModel.getProducts(ProductCategory.FLOUR_SUGAR_SALT));
        tempPantry.addAll(iMatDataModel.getProducts(ProductCategory.PASTA));
        tempPantry.addAll(iMatDataModel.getProducts(ProductCategory.POTATO_RICE));
        tempPantry.addAll(iMatDataModel.getProducts(ProductCategory.NUTS_AND_SEEDS));

        List<ProductListItem> pantry = new ArrayList<>();
        for (Product p: tempPantry) {
            pantry.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Skafferi", pantry);

        // Adds products to "Skafferi" Category
        List<Product> tempDrinks = new ArrayList<>();
        tempDrinks.addAll(iMatDataModel.getProducts(ProductCategory.COLD_DRINKS));
        tempDrinks.addAll(iMatDataModel.getProducts(ProductCategory.HOT_DRINKS));

        List<ProductListItem> drinks = new ArrayList<>();
        for (Product p: tempDrinks) {
            drinks.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Drickor", drinks);

        System.out.println("Successfully Loaded all products...");
    }

    @FXML
    public void handleSearchAction(ActionEvent event) {
        List<Product> matches = iMatDataModel.findProducts(searchTextField.getText());
        updateProductGrid(matches);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Stores all products in a map
        for (Product p: iMatDataModel.getProducts()) {
            productListItemMap.put(p.getName(), new ProductListItem(p));
        }

        shoppingCartImageView.setImage(getImageFromUrl("imat/resources/ShoppingCartButton.png"));
        profileImageView.setImage(getImageFromUrl("imat/resources/profileButton.png"));
        updateProductGrid(iMatDataModel.getProducts());

        //populateMainCategoryMap();
    }

    public Image getImageFromUrl(String url) {
        return new Image(url);
    }

    private void updateProductGrid(List<Product> products) {
        productFlowPane.getChildren().clear();
        for (Product product: products) {
            productFlowPane.getChildren().add(productListItemMap.get(product.getName()));
        }
    }
}
