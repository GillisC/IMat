package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.awt.*;

public class ImatDetaljvyController {

    private Product product;


    @FXML private AnchorPane detaljvyWindow;
    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productCategory;
    @FXML private Label productWeight;
    @FXML private Label productDetail;
    @FXML private Label productHome;
    @FXML private Label breadcrumbArrow;
    @FXML private ImageView addToCartImage;
    @FXML private ImageView closeImage;

    public productItem(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_detaljvy.fxml"));

    }
}

