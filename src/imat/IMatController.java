package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
    // Current sub category objects, used to set the backdrop style
    private ArrayList<SubCategoryItem> subCategoryItems = new ArrayList<>();

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
        List<ProductListItem> greens = new ArrayList<>();
        greens.addAll(findMatchingProducts(ProductCategory.CABBAGE));
        greens.addAll(findMatchingProducts(ProductCategory.HERB));
        greens.addAll(findMatchingProducts(ProductCategory.POD));
        greens.addAll(findMatchingProducts(ProductCategory.ROOT_VEGETABLE));
        mainCategoryMap.put("Grönsaker", greens);

        String[] greenSub = {"Kålväxter", "Örter", "Baljväxter", "Rotfrukter"};
        subCategoryMap.put("Grönsaker", List.of(greenSub));

        // Adds products to "Frukt" Category
        List<ProductListItem> fruit = new ArrayList<>();
        fruit.addAll(findMatchingProducts(ProductCategory.BERRY));
        fruit.addAll(findMatchingProducts(ProductCategory.CITRUS_FRUIT));
        fruit.addAll(findMatchingProducts(ProductCategory.EXOTIC_FRUIT));
        fruit.addAll(findMatchingProducts(ProductCategory.MELONS));
        fruit.addAll(findMatchingProducts(ProductCategory.FRUIT));
        mainCategoryMap.put("Frukt", fruit);

        String[] fruitSub = {"Bär", "Citrus Frukter", "Exotiska Frukter", "Meloner", "Frukt"};
        subCategoryMap.put("Frukt", List.of(fruitSub));

        // Adds products to "Mejeri" Category
        List<ProductListItem> dairy = new ArrayList<>(findMatchingProducts(ProductCategory.DAIRIES));
        mainCategoryMap.put("Mejeri", dairy);

        subCategoryMap.put("Mejeri", null);

        // Adds products to "Bröd" Category
        List<ProductListItem> bread = new ArrayList<>(findMatchingProducts(ProductCategory.BREAD));
        mainCategoryMap.put("Bröd", bread);

        subCategoryMap.put("Bröd", null);

        // Adds products to "Kött" Category
        List<ProductListItem> meat = new ArrayList<>(findMatchingProducts(ProductCategory.MEAT));
        mainCategoryMap.put("Kött", meat);

        subCategoryMap.put("Kött", null);

        // Adds products to "Fisk" Category
        List<ProductListItem> fish = new ArrayList<>(findMatchingProducts(ProductCategory.FISH));
        mainCategoryMap.put("Fisk", fish);

        subCategoryMap.put("Fisk", null);

        // Adds products to "Skafferi" Category
        List<ProductListItem> pantry = new ArrayList<>();
        pantry.addAll(findMatchingProducts(ProductCategory.FLOUR_SUGAR_SALT));
        pantry.addAll(findMatchingProducts(ProductCategory.PASTA));
        pantry.addAll(findMatchingProducts(ProductCategory.POTATO_RICE));
        pantry.addAll(findMatchingProducts(ProductCategory.NUTS_AND_SEEDS));
        mainCategoryMap.put("Skafferi", pantry);

        String[] pantrySub = {"Mjöl, Socker & Salt", "Pasta", "Potatis & Ris", "Nötter, & Frön"};
        subCategoryMap.put("Skafferi", List.of(pantrySub));

        // Adds products to "Skafferi" Category
        List<ProductListItem> drinks = new ArrayList<>();
        drinks.addAll(findMatchingProducts(ProductCategory.COLD_DRINKS));
        drinks.addAll(findMatchingProducts(ProductCategory.HOT_DRINKS));
        mainCategoryMap.put("Drickor", drinks);

        String[] drinksSub = {"Kalla Drycker", "Varma Drycker"};
        subCategoryMap.put("Drickor", List.of(drinksSub));

        System.out.println("Successfully Loaded all products...");
    }

    @FXML
    public void handleSearchAction(ActionEvent event) {
        /* Prevents it from creating useless objects */
        if (Objects.equals(searchTextField.getText(), "")) {
            return;
        }
        String searchTerm = searchTextField.getText();
        List<ProductListItem> matches = findMatchingProducts(searchTerm);
        updateProductGrid(matches);
    }

    private List<ProductListItem> findMatchingProducts(String s) {
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

    private List<ProductListItem> findMatchingProducts(ProductCategory category) {
        List<ProductListItem> result = new ArrayList<>();
        for (ProductListItem p: mainCategoryMap.get("Allt")) {
            ProductCategory pCategory = p.getProductListItemCategory();
            if (pCategory == category) {
                result.add(p);
            }
        }
        return result;
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
        subCategoryItems.clear();
        List<String> subCategories = subCategoryMap.get(selectedCategory);
        if (subCategories == null) {
            return;
        }
        for (String s: subCategories) {
            SubCategoryItem subCategoryItem = new SubCategoryItem(s, this);
            subCategoryItems.add(subCategoryItem);
            subCategoryFlowPane.getChildren().add(subCategoryItem);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCategory = "Allt";

        populateMainCategoryMap();
        updateCategoryImages(selectedCategory);
        updateProductGrid(mainCategoryMap.get(selectedCategory));

        shoppingCartImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/ShoppingCartButton.png"));
        profileImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/profileButton.png"));
    }

    /* Updates the product with given list of products */
    private void updateProductGrid(List<ProductListItem> products) {
        productFlowPane.getChildren().clear();
        for (ProductListItem product: products) {
            productFlowPane.getChildren().add(product);
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
        // resets the background color on all category buttons
        for (SubCategoryItem subCategoryItem : subCategoryItems) {
            subCategoryItem.backdropAnchorPane.setStyle("-fx-background-color: #E3E3E3");
        }
        ProductCategory category = getProductCategory(subCategory);
        updateProductGrid(findMatchingProducts(category));
    }

    public void setMainCategory(String categoryName) {
        selectedCategory = categoryName;
        updateCategoryImages(selectedCategory);
        populateSubCategory();
        updateProductGrid(mainCategoryMap.get(selectedCategory));
    }

    public ProductCategory getProductCategory(String subCategory) {
        return switch (subCategory) {
            case "Kålväxter" -> ProductCategory.CABBAGE;
            case "Örter" -> ProductCategory.HERB;
            case "Baljväxter" -> ProductCategory.POD;
            case "Rotfrukter" -> ProductCategory.ROOT_VEGETABLE;
            case "Bär" -> ProductCategory.BERRY;
            case "Citrus Frukter" -> ProductCategory.CITRUS_FRUIT;
            case "Exotiska Frukter" -> ProductCategory.EXOTIC_FRUIT;
            case "Meloner" -> ProductCategory.MELONS;
            case "Frukt" -> ProductCategory.FRUIT;
            case "Mjöl, Socker & Salt" -> ProductCategory.FLOUR_SUGAR_SALT;
            case "Pasta" -> ProductCategory.PASTA;
            case "Potatis & Ris" -> ProductCategory.POTATO_RICE;
            case "Nötter & Frön" -> ProductCategory.NUTS_AND_SEEDS;
            case "Kalla Drycker" -> ProductCategory.COLD_DRINKS;
            case "Varma Drycker" -> ProductCategory.HOT_DRINKS;
            default -> null;
        };
    }
}
