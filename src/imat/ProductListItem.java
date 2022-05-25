package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Objects;

public class ProductListItem extends AnchorPane {

    private IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    private Product product;

    @FXML ImageView productImageView, ekoImageView;
    @FXML Label productNameLabel, productUnitSuffixLabel, productAmountLabel, productPriceLabel;
    @FXML Button addButton, removeButton;
    @FXML TextField productAmountTextField;
    @FXML AnchorPane listItemAnchorPane;

    IMatController parentController;

    public ProductListItem(Product product, IMatController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_listitem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.parentController = controller;

        Image image = iMatDataModel.getFXImage(product);
        productImageView.setImage(image);
        productNameLabel.setText(product.getName());
        productPriceLabel.setText(product.getPrice() + " kr");
        productUnitSuffixLabel.setText("/" + product.getUnitSuffix());
        productAmountTextField.setText("0");
        if (product.isEcological()) {
            ekoImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/eko-märkt.jpg"));
        }

        iMatDataModel.setOnHover(addButton);
        iMatDataModel.setOnHover(removeButton);
        iMatDataModel.setOnHover(listItemAnchorPane);
    }
    protected String getCorrectSuffix() {
        String suffix = product.getUnitSuffix();
        if (Objects.equals(suffix, "kg")) {
            return "kg";
        } else {
            return "st";
        }
    }
    @FXML
    private void handleAddAction(ActionEvent event) {
        parentController.handleAddProduct(product);
    }

    @FXML
    private void handleRemoveAction(ActionEvent event) {
        parentController.handleRemoveProduct(product);
    }

    @FXML
    private void onClick() {
        parentController.populateDetailView(product);
    }

    protected ProductCategory getProductListItemCategory() {
        return product.getCategory();
    }
    protected String getProductListItemName() {
        return product.getName();
    }
}
