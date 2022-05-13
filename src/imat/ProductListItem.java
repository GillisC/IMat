package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductListItem extends AnchorPane {

    private IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    private Product product;

    @FXML ImageView productImageView;
    @FXML Label productNameLabel;
    @FXML Label productPriceLabel;
    @FXML Label productUnitSuffixLabel;
    @FXML Button addButton;
    @FXML Button removeButton;

    public ProductListItem(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("yo");
            throw new RuntimeException(exception);
        }

        this.product = product;

        Image image = iMatDataModel.getFXImage(product);
        productImageView.setImage(image);
        productNameLabel.setText(product.getName());
        productPriceLabel.setText(product.getPrice() + " kr");
        productUnitSuffixLabel.setText("/" + product.getUnitSuffix());
    }
    @FXML
    private void handleAddAction(ActionEvent event) {
        System.out.println("Added: " + product.getName());
    }
    @FXML
    private void handleRemoveAction(ActionEvent event) {
        System.out.println("Removed: " + product.getName());
    }

}
