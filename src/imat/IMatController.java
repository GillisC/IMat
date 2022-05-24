package imat;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static se.chalmers.cse.dat216.project.ProductCategory.CABBAGE;
import static se.chalmers.cse.dat216.project.ProductCategory.ROOT_VEGETABLE;

public class IMatController implements Initializable, ShoppingCartListener, ShoppingCartManager {

    private IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    iMatComplete iMatComplete;

    // Current sub category objects, used to set the backdrop style
    private ArrayList<SubCategoryItem> subCategoryItems = new ArrayList<>();

    private String selectedCategory = "Allt";
    // Keeps track of where we start to render the carousel
    private int categoryIndex = 0;

    /* Main view */
    @FXML private ImageView profileImageView;
    @FXML private ImageView shoppingCartImageView;
    @FXML private TextField searchTextField;
    @FXML private Button searchButton;
    @FXML private FlowPane productFlowPane;
    @FXML private FlowPane subCategoryFlowPane;
    @FXML private ScrollPane mainCategoryScrollPane;
    @FXML private FlowPane mainCategoryFlowPane;
    @FXML private StackPane mainStackPane;
    @FXML private ImageView leftNavigationImageView;
    @FXML private ImageView rightNavigationImageView;
    @FXML private AnchorPane mainAnchorPane, backAnchorPane;

    /* Shopping cart view */
    @FXML private AnchorPane shoppingCartAnchorPane;
    @FXML private AnchorPane shoppingCartBackAnchorPane;
    @FXML private FlowPane shoppingCartFlowPane;
    @FXML private Label shoppingCartButtonTotalLabel;
    @FXML private Label shoppingCartViewTotalLabel;
    @FXML private ImageView shoppingCartCloseImage, clearShoppingCartImage;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (iMatDataModel.mainCategoryMap.isEmpty()) {
            populateMainCategoryMap();
        }
        iMatDataModel.getShoppingCart().addShoppingCartListener(this);

        updateCategoryImages();
        updateProductGrid(iMatDataModel.mainCategoryMap.get(selectedCategory));

        profileImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/icons/receipt.png"));
        shoppingCartImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/icons/shopping-cart.png"));

        leftNavigationImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/left-navigation-triangle.png"));
        rightNavigationImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/right-navigation-triangle.png"));

        searchTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleSearchAction();
            }
        });
        updateShoppingCart();
        updateProductItemsAmount();
    }

    private void populateMainCategoryMap() {

        // Adds products to "Allt" category
        List<Product> tempEverything = iMatDataModel.getProducts();
        List<ProductListItem> everything = new ArrayList<>();
        for (Product p: tempEverything) {
            everything.add(new ProductListItem(p, this));
        }
        iMatDataModel.mainCategoryMap.put("Allt", everything);
        iMatDataModel.subCategoryMap.put("Allt", null);

        // Adds products to "Grönsaker" Category
        List<ProductListItem> greens = new ArrayList<>();
        greens.addAll(iMatDataModel.findMatchingProducts(CABBAGE));
        greens.addAll(iMatDataModel.findMatchingProducts(ProductCategory.HERB));
        greens.addAll(iMatDataModel.findMatchingProducts(ProductCategory.POD));
        greens.addAll(iMatDataModel.findMatchingProducts(ROOT_VEGETABLE));
        iMatDataModel.mainCategoryMap.put("Grönsaker", greens);

        String[] greenSub = {"Kålväxter", "Örter", "Baljväxter", "Rotfrukter"};
        iMatDataModel.subCategoryMap.put("Grönsaker", List.of(greenSub));

        // Adds products to "Frukt" Category
        List<ProductListItem> fruit = new ArrayList<>();
        fruit.addAll(iMatDataModel.findMatchingProducts(ProductCategory.BERRY));
        fruit.addAll(iMatDataModel.findMatchingProducts(ProductCategory.CITRUS_FRUIT));
        fruit.addAll(iMatDataModel.findMatchingProducts(ProductCategory.EXOTIC_FRUIT));
        fruit.addAll(iMatDataModel.findMatchingProducts(ProductCategory.MELONS));
        fruit.addAll(iMatDataModel.findMatchingProducts(ProductCategory.FRUIT));
        iMatDataModel.mainCategoryMap.put("Frukt", fruit);

        String[] fruitSub = {"Bär", "Citrus Frukter", "Exotiska Frukter", "Meloner", "Frukt"};
        iMatDataModel.subCategoryMap.put("Frukt", List.of(fruitSub));

        // Adds products to "Mejeri" Category
        List<ProductListItem> dairy = new ArrayList<>(iMatDataModel.findMatchingProducts(ProductCategory.DAIRIES));
        iMatDataModel.mainCategoryMap.put("Mejeri", dairy);

        iMatDataModel.subCategoryMap.put("Mejeri", null);

        // Adds products to "Bröd" Category
        List<ProductListItem> bread = new ArrayList<>(iMatDataModel.findMatchingProducts(ProductCategory.BREAD));
        iMatDataModel.mainCategoryMap.put("Bröd", bread);

        iMatDataModel.subCategoryMap.put("Bröd", null);

        // Adds products to "Kött" Category
        List<ProductListItem> meat = new ArrayList<>(iMatDataModel.findMatchingProducts(ProductCategory.MEAT));
        iMatDataModel.mainCategoryMap.put("Kött", meat);

        iMatDataModel.subCategoryMap.put("Kött", null);

        // Adds products to "Fisk" Category
        List<ProductListItem> fish = new ArrayList<>(iMatDataModel.findMatchingProducts(ProductCategory.FISH));
        iMatDataModel.mainCategoryMap.put("Fisk", fish);

        iMatDataModel.subCategoryMap.put("Fisk", null);

        // Adds products to "Skafferi" Category
        List<ProductListItem> pantry = new ArrayList<>();
        pantry.addAll(iMatDataModel.findMatchingProducts(ProductCategory.FLOUR_SUGAR_SALT));
        pantry.addAll(iMatDataModel.findMatchingProducts(ProductCategory.PASTA));
        pantry.addAll(iMatDataModel.findMatchingProducts(ProductCategory.POTATO_RICE));
        pantry.addAll(iMatDataModel.findMatchingProducts(ProductCategory.NUTS_AND_SEEDS));
        iMatDataModel.mainCategoryMap.put("Skafferi", pantry);

        String[] pantrySub = {"Mjöl, Socker & Salt", "Pasta", "Potatis & Ris", "Nötter, & Frön"};
        iMatDataModel.subCategoryMap.put("Skafferi", List.of(pantrySub));

        // Adds products to "Skafferi" Category
        List<ProductListItem> drinks = new ArrayList<>();
        drinks.addAll(iMatDataModel.findMatchingProducts(ProductCategory.COLD_DRINKS));
        drinks.addAll(iMatDataModel.findMatchingProducts(ProductCategory.HOT_DRINKS));
        iMatDataModel.mainCategoryMap.put("Drickor", drinks);

        String[] drinksSub = {"Kalla Drycker", "Varma Drycker"};
        iMatDataModel.subCategoryMap.put("Drickor", List.of(drinksSub));

        System.out.println("Successfully Loaded all products...");
    }

    @FXML
    public void handleSearchAction() {
        /* Prevents it from creating useless objects */
        if (Objects.equals(searchTextField.getText(), "")) {
            return;
        }
        String searchTerm = searchTextField.getText();
        List<ProductListItem> matches = iMatDataModel.findMatchingProducts(searchTerm);
        updateProductGrid(matches);
    }



    private void updateCategoryImages() {
        String[] categoryArray = {"Allt", "Grönsaker", "Frukt", "Mejeri", "Bröd", "Kött", "Fisk", "Skafferi", "Drickor"};
        mainCategoryFlowPane.getChildren().clear();
        for (int i = categoryIndex; i < categoryIndex + 5; i++) {
            mainCategoryFlowPane.getChildren().add(new CategoryItem(categoryArray[i], selectedCategory, this));
        }
    }

    private void populateSubCategory() {
        subCategoryFlowPane.getChildren().clear();
        subCategoryItems.clear();
        List<String> subCategories = iMatDataModel.subCategoryMap.get(selectedCategory);
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
        ProductCategory category = iMatDataModel.getProductCategory(subCategory);
        updateProductGrid(iMatDataModel.findMatchingProducts(category));
    }
    /* Called when main category icon is pressed */
    public void setMainCategory(String categoryName) {
        selectedCategory = categoryName;
        updateCategoryImages();
        populateSubCategory();
        updateProductGrid(iMatDataModel.mainCategoryMap.get(selectedCategory));
    }

    private void updateShoppingCart() {
        shoppingCartFlowPane.getChildren().clear();
        List<ShoppingItem> shoppingItemsList = iMatDataModel.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : shoppingItemsList){
            shoppingCartFlowPane.getChildren().add(new VarukorgItem(shoppingItem, this));
        }

        shoppingCartButtonTotalLabel.setText(iMatDataModel.round(iMatDataModel.getShoppingCart().getTotal(), 2) + " kr");
        shoppingCartViewTotalLabel.setText("Totalt: " + iMatDataModel.round(iMatDataModel.getShoppingCart().getTotal(), 2) + " kr");
    }

    @FXML
    protected void openShoppingCartView() {
        shoppingCartBackAnchorPane.toFront();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(shoppingCartAnchorPane);
        transition.setByX(-460);
        transition.play();
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        updateShoppingCart();
    }

    public void handleAddProduct(Product product) {
        iMatDataModel.addToShoppingCart(product);
        updateShoppingCart();
        updateProductItemsAmount();
    }

    public void handleRemoveProduct(Product product) {
        iMatDataModel.removeFromShoppingCart(product);
        updateShoppingCart();
        updateProductItemsAmount();
    }

    /* Updates the productListItems with the value stored in the shopping cart */
    private void updateProductItemsAmount() {
        List<ShoppingItem> shoppingItems = iMatDataModel.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : shoppingItems) {
            ProductListItem productListItem = iMatDataModel.findMatchingProducts(shoppingItem.getProduct().getName()).get(0);
            if (iMatDataModel.round(shoppingItem.getAmount(), 1) == 0.0) {
                productListItem.productAmountTextField.setText("0");
            }
            else if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "kg")) {
                productListItem.productAmountTextField.setText(iMatDataModel.round(shoppingItem.getAmount(), 1) + productListItem.getCorrectSuffix());
            } else {
                productListItem.productAmountTextField.setText((int) shoppingItem.getAmount() + productListItem.getCorrectSuffix());
            }
        }
    }

    private void resetProductAmounts() {
        for (ProductListItem productListItem : iMatDataModel.mainCategoryMap.get("Allt")) {
            productListItem.productAmountTextField.setText("0");
        }
    }

    @FXML
    public void payButtonPressed() {
        if (iMatDataModel.getShoppingCart().getTotal() == 0) {
            return;
        }
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
        detailViewCategoryLabel.setText(iMatDataModel.getProductCategoryName(product.getCategory()));
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
    public void handleNavigateRight() {
        if (!(categoryIndex >= 4)) {
            categoryIndex++;
            updateCategoryImages();
        }
    }

    @FXML
    public void handleNavigateLeft() {
        if (!(categoryIndex <= 0)) {
            categoryIndex--;
            updateCategoryImages();
        }
    }
    @FXML
    public void closeButtonExitHover() {
        closeButtonImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-blue.png"));
    }

    @FXML
    public void leftNavigationArrowHover() {
        leftNavigationImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/left-navigation-triangle-hover.png"));
    }

    @FXML
    public void leftNavigationArrowExitHover() {
        leftNavigationImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/left-navigation-triangle.png"));
    }

    @FXML
    public void rightNavigationArrowHover() {
        rightNavigationImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/right-navigation-triangle-hover.png"));
    }

    @FXML
    public void rightNavigationArrowExitHover() {
        rightNavigationImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/right-navigation-triangle.png"));
    }

    @FXML
    protected void closeShoppingCartView() {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(shoppingCartAnchorPane);
        transition.setByX(460);
        transition.play();
        mainAnchorPane.toFront();
    }

    @FXML
    public void closeShoppingCartHover() {
        shoppingCartCloseImage.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-white-hover.png"));
    }

    @FXML
    public void closeShoppingCartExitHover() {
        shoppingCartCloseImage.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-white.png"));
    }

    @FXML
    public void handleClearShoppingCart() {
        iMatDataModel.getShoppingCart().clear();
        resetProductAmounts();
        updateShoppingCart();
    }

    @FXML
    public void clearShoppingCartHover() {
        clearShoppingCartImage.setImage(iMatDataModel.getImageFromUrl("imat/resources/delete-hover.png"));
    }

    @FXML
    public void clearShoppingCartExitHover() {
        clearShoppingCartImage.setImage(iMatDataModel.getImageFromUrl("imat/resources/delete.png"));
    }

    @FXML
    private void navigateToOrderHistory() {
        System.out.println("Yo");
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("order_history.fxml"));
            mainAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
