package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    private IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    private Map<String, ProductListItem> productListItemMap = new HashMap<>();

    @FXML private ImageView shoppingCartImageView;
    @FXML private ImageView profileImageView;
    @FXML private TextField searchTextField;
    @FXML private Button searchButton;
    @FXML private ComboBox<String> sortComboBox;
    @FXML private FlowPane productFlowPane;
    @FXML private FlowPane subCategoryFlowPane;

    @FXML private AnchorPane shoppingCartPane;
    @FXML private AnchorPane backgroundPane;
    @FXML private FlowPane shoppingItemList;
    @FXML private Label totalLabel;
    @FXML private ImageView closeButton;
    @FXML private ImageView payButton;

    @FXML
    public void handleSearchAction(ActionEvent event) {
        List<Product> matches = iMatDataModel.findProducts(searchTextField.getText());
        updateProductGrid(matches);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingCartImageView.setImage(getImageFromUrl("imat/resources/ShoppingCartButton.png"));
        profileImageView.setImage(getImageFromUrl("imat/resources/profileButton.png"));
        updateProductGrid(iMatDataModel.getProducts());
        updateShoppingCart(iMatDataModel.getShoppingCart().getItems());
        totalLabel.setText("Totalt: " + iMatDataModel.getShoppingCart().getTotal() + " kr");

    }
    public Image getImageFromUrl(String url) {
        return new Image(url);
    }
    private void updateProductGrid(List<Product> products) {
        productFlowPane.getChildren().clear();
        for (Product product: products) {
            productFlowPane.getChildren().add(new ProductListItem(product));
        }

    }
    private void updateShoppingCart(List<ShoppingItem> shoppingItems){
        shoppingItemList.getChildren().clear();
        for (ShoppingItem shoppingItem : shoppingItems){
            shoppingItemList.getChildren().add(new VarukorgItemController(iMatDataModel, shoppingItem));
        }
    }
    //onClick på varukorg gör bring to front på varukorgen
    //onClick på close button eller det gråa gör bring to front på main sidan
    //onClick på Betala öppnar betalningsvy.
}
