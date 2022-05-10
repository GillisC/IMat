package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import se.chalmers.cse.dat216.project.Product;

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
    @FXML private GridPane productGridPane;
    @FXML private FlowPane subCategoryFlowPane;

    @FXML
    public void handleSearchAction(ActionEvent event) {
        List<Product> matches = iMatDataModel.findProducts(searchTextField.getText());
        updateProductGrid(matches);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //updateProductGrid(iMatDataModel.getProducts());
    }

    private void updateProductGrid(List<Product> products) {
        productGridPane.getChildren().clear();
        for (Product p: products) {
            productGridPane.getChildren().add(new ProductListItem(p));
        }
    }
}

