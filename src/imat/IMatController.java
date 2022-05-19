package imat;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static se.chalmers.cse.dat216.project.ProductCategory.CABBAGE;
import static se.chalmers.cse.dat216.project.ProductCategory.ROOT_VEGETABLE;

public class IMatController implements Initializable, ShoppingCartListener {

    private IMatDataModel iMatDataModel = IMatDataModel.getInstance();

    // Main Category maps to a list of products that belong in that category
    private final Map<String, List<ProductListItem>> mainCategoryMap = new HashMap<>();

    // Maps A main category to their respective subcategories
    private final Map<String, List<String>> subCategoryMap = new HashMap<>();
    // Current sub category objects, used to set the backdrop style
    private ArrayList<SubCategoryItem> subCategoryItems = new ArrayList<>();

    private String selectedCategory;

    /* Main view */
    @FXML private ImageView shoppingCartImageView;
    @FXML private ImageView profileImageView;
    @FXML private TextField searchTextField;
    @FXML private Button searchButton;
    @FXML private FlowPane productFlowPane;
    @FXML private FlowPane subCategoryFlowPane;
    @FXML private ScrollPane mainCategoryScrollPane;
    @FXML private FlowPane mainCategoryFlowPane;
    @FXML private StackPane mainStackPane;

    /* Shopping cart view */
    @FXML private AnchorPane shoppingCartAnchorPane;
    @FXML private AnchorPane shoppingCartBackAnchorPane;
    @FXML private AnchorPane mainAnchorPane;
    @FXML private FlowPane shoppingCartFlowPane;
    @FXML private Label shoppingCartButtonTotalLabel;
    @FXML private Label shoppingCartViewTotalLabel;
    @FXML private ImageView closeButton;
    @FXML private Button ShoppingCartPayButton;

    /* Detail view */
    @FXML private AnchorPane detailViewAnchorPane;
    @FXML private ImageView detailViewImageView;
    @FXML private ImageView closeButtonImageView;
    @FXML private Label detailViewNameLabel;
    @FXML private Label detailViewCategoryLabel;
    @FXML private Label detailViewUnitSuffixLabel;
    @FXML private TextArea detailViewDescriptionTextArea;
    @FXML private Button detailViewRemoveButton;
    @FXML private Button detailViewAddButton;


    /* Populates mainCategoryMap so that a category name maps to a list of products */
    private void populateMainCategoryMap() {

        // Adds products to "Allt" category
        List<Product> tempEverything = iMatDataModel.getProducts();
        List<ProductListItem> everything = new ArrayList<>();
        for (Product p: tempEverything) {
            everything.add(new ProductListItem(p, this));
        }
        mainCategoryMap.put("Allt", everything);
        subCategoryMap.put("Allt", null);

        // Adds products to "Grönsaker" Category
        List<ProductListItem> greens = new ArrayList<>();
        greens.addAll(findMatchingProducts(CABBAGE));
        greens.addAll(findMatchingProducts(ProductCategory.HERB));
        greens.addAll(findMatchingProducts(ProductCategory.POD));
        greens.addAll(findMatchingProducts(ROOT_VEGETABLE));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iMatDataModel.getShoppingCart().addShoppingCartListener(this);

        selectedCategory = "Allt";

        populateMainCategoryMap();
        updateCategoryImages(selectedCategory);
        updateProductGrid(mainCategoryMap.get(selectedCategory));

        shoppingCartImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/icons/shopping-cart.png"));
        profileImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/icons/receipt.png"));

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

    /* Updates the product with given list of products */
    private void updateProductGrid(List<ProductListItem> products) {
        productFlowPane.getChildren().clear();
        for (ProductListItem product: products) {
            productFlowPane.getChildren().add(product);
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
    /* Called when main category icon is pressed */
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

    public String getProductCategoryName(ProductCategory category) {
        return switch (category) {
            case CABBAGE ->"Kålväxter";
            case HERB ->"Örter" ;
            case POD -> "Baljväxter";
            case ROOT_VEGETABLE -> "Rotfrukter" ;
            case BERRY -> "Bär";
            case CITRUS_FRUIT -> "Citrus Frukter";
            case EXOTIC_FRUIT -> "Exotiska Frukter";
            case MELONS -> "Meloner";
            case FRUIT -> "Frukt";
            case FLOUR_SUGAR_SALT -> "Mjöl, Socker & Salt";
            case PASTA -> "Pasta";
            case POTATO_RICE -> "Potatis & Ris";
            case NUTS_AND_SEEDS -> "Nötter & Frön";
            case COLD_DRINKS -> "Kalla Drycker";
            case HOT_DRINKS -> "Varma Drycker";
            case MEAT -> "Kött";
            case VEGETABLE_FRUIT -> "Grönsaker";
            default -> null;
        };
    }

    private void updateShoppingCart() {
        shoppingCartFlowPane.getChildren().clear();
        List<ShoppingItem> shoppingItemsList = iMatDataModel.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : shoppingItemsList){
            shoppingCartFlowPane.getChildren().add(new VarukorgItem(shoppingItem, this));
        }
        System.out.println("Total: " + iMatDataModel.getShoppingCart().getTotal() + " kr");
        shoppingCartButtonTotalLabel.setText(iMatDataModel.round(iMatDataModel.getShoppingCart().getTotal(), 2) + " kr");
        shoppingCartViewTotalLabel.setText("Totalt: " + iMatDataModel.round(iMatDataModel.getShoppingCart().getTotal(), 2) + " kr");
    }

    @FXML
    protected void openShoppingCartView() {
        shoppingCartBackAnchorPane.toFront();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(shoppingCartAnchorPane);
        transition.setByX(-410);
        transition.play();
    }

    @FXML
    protected void closeShoppingCartView() {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(shoppingCartAnchorPane);
        transition.setByX(410);
        transition.play();
        mainAnchorPane.toFront();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateShoppingCart();
    }

    protected void handleAddProduct(Product product) {
        iMatDataModel.addToShoppingCart(product);
        ProductListItem correspondingProduct = findMatchingProducts(product.getName()).get(0);
        correspondingProduct.incrementAmountLabel();
        updateShoppingCart();
    }

    protected void handleRemoveProduct(Product product) {
        iMatDataModel.removeFromShoppingCart(product);
        ProductListItem correspondingProduct = findMatchingProducts(product.getName()).get(0);
        correspondingProduct.decrementAmountLabel();
        updateShoppingCart();
    }

    @FXML
    public void payButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("IMatSelectDateTime.fxml"));
            shoppingCartBackAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateDetailView(Product product) {
        detailViewImageView.setImage(iMatDataModel.getFXImage(product));
        detailViewNameLabel.setText(product.getName());
        detailViewCategoryLabel.setText(getProductCategoryName(product.getCategory()));
        detailViewUnitSuffixLabel.setText(String.valueOf(product.getPrice()) + product.getUnit());

        detailViewAnchorPane.toFront();

    }
    @FXML
    public void closeDetailView() {
        mainAnchorPane.toFront();
    }

    @FXML
    public void closeButtonHover() {
        closeButtonImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-blue-hover.png"));
    }

    @FXML
    public void closeButtonExitHover() {
        closeButtonImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-blue.png"));
    }
}
