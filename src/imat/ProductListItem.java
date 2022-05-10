package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Product;

import java.awt.*;
import java.io.IOException;

public class ProductListItem extends AnchorPane {

    private IMatDataModel iMatDataModel =  IMatDataModel.getInstance();
    private Product product;

    @FXML private ImageView productImageView;
    @FXML private Label productNameLabel;
    @FXML private Label productPriceLabel;
    @FXML private Label productSuffixLabel;

    public ProductListItem(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;

        Image image = iMatDataModel.getFXImage(product);
        this.productImageView.setImage(image);

        this.productNameLabel.setText(product.getName());

        this.productPriceLabel.setText(String.valueOf(product.getPrice()));

        this.productSuffixLabel.setText(product.getUnit());
    }


}
