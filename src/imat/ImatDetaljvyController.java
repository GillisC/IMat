package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ImatDetaljvyController extends AnchorPane{
    private IMatDataModel dataModel;
    private IMat imat;
    private Product product;

    @FXML
    private AnchorPane detaljvyWindow;
    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productCategory;
    @FXML
    private Label productWeight;
    @FXML
    private Label productDetail;
    @FXML
    private Label productHome;
    @FXML
    private Label breadcrumbArrow;
    @FXML
    private ImageView addToCartImage;
    @FXML
    private ImageView closeImage;
    @FXML
    private Button addToCartButton;
    @FXML
    private Button closeButton;


 public void productItem(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_detaljvy.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.dataModel = dataModel;
        this.imat = imat;
        Image image = dataModel.getFXImage(product);

        productImage.setImage(image);
        productName.setText(product.getName());
        productCategory.setText(String.valueOf(product.getCategory()));
        productWeight.setText(String.valueOf(product.getPrice()) + "/" + product.getUnit());
 }
     @FXML
     protected void clickedAddToCart(){
        dataModel.addProduct(product);
     }


}
