package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {

    private IMatDataModel iMatDataModel = IMatDataModel.getInstance();

    // Main Category maps to a list of products that belong in that category
    private final Map<String, List<ProductListItem>> mainCategoryMap = new HashMap<>();

    // Maps A main category to their respective subcategories
    private final Map<String, List<String>> subCategoryMap = new HashMap<>();
    private String selectedCategory;

    @FXML private ImageView shoppingCartImageView;
    @FXML private ImageView profileImageView;
    @FXML private TextField searchTextField;
    @FXML private Button searchButton;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private FlowPane productFlowPane;
    @FXML private FlowPane subCategoryFlowPane;
    @FXML private ScrollPane mainCategoryScrollPane;
    @FXML private FlowPane mainCategoryFlowPane;
    @FXML private StackPane mainStackPane;

    /* Populates mainCategoryMap so that a category name maps to a list of products */
    private void populateMainCategoryMap() {

        // Adds products to "Allt" category
        List<Product> tempEverything = iMatDataModel.getProducts();
        List<ProductListItem> everything = new ArrayList<>();
        for (Product p: tempEverything) {
            everything.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Allt", everything);
        subCategoryMap.put("Allt", null);

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
        String[] greenSub = {"Kålväxter", "Örter", "Baljväxter", "Rotfrukter"};
        subCategoryMap.put("Grönsaker", List.of(greenSub));

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
        String[] fruitSub = {"Bär", "Citrus Frukter", "Exotiska Frukter", "Meloner", "Frukt"};
        subCategoryMap.put("Frukt", List.of(fruitSub));

        // Adds products to "Mejeri" Category
        List<Product> tempDairy = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.DAIRIES));

        List<ProductListItem> dairy = new ArrayList<>();
        for (Product p: tempDairy) {
            dairy.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Mejeri", dairy);
        subCategoryMap.put("Mejeri", null);

        // Adds products to "Bröd" Category
        List<Product> tempBread = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.BREAD));

        List<ProductListItem> bread = new ArrayList<>();
        for (Product p: tempBread) {
            bread.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Bröd", bread);
        subCategoryMap.put("Bröd", null);

        // Adds products to "Kött" Category
        List<Product> tempMeat = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.MEAT));

        List<ProductListItem> meat = new ArrayList<>();
        for (Product p: tempMeat) {
            meat.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Kött", meat);
        subCategoryMap.put("Kött", null);

        // Adds products to "Fisk" Category
        List<Product> tempFish = new ArrayList<>(iMatDataModel.getProducts(ProductCategory.FISH));

        List<ProductListItem> fish = new ArrayList<>();
        for (Product p: tempFish) {
            fish.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Fisk", fish);
        subCategoryMap.put("Fisk", null);

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
        String[] pantrySub = {"Mjöl, Socker & Salt", "Pasta", "Potatis & Ris", "Nötter, & Frön"};
        subCategoryMap.put("Skafferi", List.of(pantrySub));

        // Adds products to "Skafferi" Category
        List<Product> tempDrinks = new ArrayList<>();
        tempDrinks.addAll(iMatDataModel.getProducts(ProductCategory.COLD_DRINKS));
        tempDrinks.addAll(iMatDataModel.getProducts(ProductCategory.HOT_DRINKS));

        List<ProductListItem> drinks = new ArrayList<>();
        for (Product p: tempDrinks) {
            drinks.add(new ProductListItem(p));
        }
        mainCategoryMap.put("Drickor", drinks);
        String[] drinksSub = {"Kalla Drycker", "Varma Drycker"};
        subCategoryMap.put("Drickor", List.of(drinksSub));

        System.out.println("Successfully Loaded all products...");
    }

    @FXML
    public void handleSearchAction(ActionEvent event) {
        /* Prevents it from creating useless objects */
        if (searchTextField.getText() == "") {
            updateProductGrid();
            return;
        }
        List<Product> matches = iMatDataModel.findProducts(searchTextField.getText());
        updateProductGrid(matches);
    }

    private void updateCategoryImages(String selected) {
        String[] categoryArray = {"Allt", "Grönsaker", "Frukt", "Mejeri", "Bröd", "Kött", "Fisk", "Skafferi", "Drickor"};
        mainCategoryFlowPane.getChildren().clear();

        for (String s: categoryArray) {
            mainCategoryFlowPane.getChildren().add(new CategoryItem(s, selectedCategory, this));
        }
    }

    private void populateSubCategory() {
        subCategoryFlowPane.getChildren().clear();
        List<String> subCategories = subCategoryMap.get(selectedCategory);
        if (subCategories == null) {
            return;
        }
        for (String s: subCategories) {
            subCategoryFlowPane.getChildren().add(new SubCategoryItem(s, this));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCategory = "Allt";

        populateMainCategoryMap();
        updateCategoryImages(selectedCategory);
        updateProductGrid();

        shoppingCartImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/ShoppingCartButton.png"));
        profileImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/profileButton.png"));
    }

    /* Updates the product with given list of products */
    private void updateProductGrid(List<Product> products) {
        productFlowPane.getChildren().clear();
        for (Product product: products) {
            productFlowPane.getChildren().add(new ProductListItem(product));
        }
    }
    /* Updates the product list based on current main category */
    private void updateProductGrid() {
        productFlowPane.getChildren().clear();
        for (ProductListItem productListItem: mainCategoryMap.get(selectedCategory)) {
            productFlowPane.getChildren().add(productListItem);
        }
    }

    /* Updates only when a subcategory is chosen */
    protected void updateProductGridWithSub(String subCategory) {
        ProductCategory category = getProductCategory(subCategory);
        List<Product> products = iMatDataModel.getProducts(category);
        updateProductGrid(products);
    }

    public void setMainCategory(String categoryName) {
        selectedCategory = categoryName;
        updateCategoryImages(selectedCategory);
        populateSubCategory();
        updateProductGrid();
    }

    public ProductCategory getProductCategory(String subCategory) {
        switch (subCategory) {
            case "Kålväxter" :
                return ProductCategory.CABBAGE;
            case "Örter" :
                return ProductCategory.HERB;
            case "Baljväxter" :
                return ProductCategory.POD;
            case "Rotfrukter" :
                return ProductCategory.ROOT_VEGETABLE;
            case "Bär" :
                return ProductCategory.BERRY;
            case "Citrus Frukter" :
                return ProductCategory.CITRUS_FRUIT;
            case "Exotiska Frukter" :
                return ProductCategory.EXOTIC_FRUIT;
            case "Meloner" :
                return ProductCategory.MELONS;
            case "Frukt" :
                return ProductCategory.FRUIT;
            case "Mjöl, Socker & Salt" :
                return ProductCategory.FLOUR_SUGAR_SALT;
            case "Pasta" :
                return ProductCategory.PASTA;
            case "Potatis & Ris" :
                return ProductCategory.POTATO_RICE;
            case "Nötter & Frön" :
                return ProductCategory.NUTS_AND_SEEDS;
            case "Kalla Drycker" :
                return ProductCategory.COLD_DRINKS;
            case "Varma Drycker" :
                return ProductCategory.HOT_DRINKS;
            default:
                return null;
        }
    }
}
