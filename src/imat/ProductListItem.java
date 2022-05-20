package imat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    ImageView productImageView;
    @FXML
    Label productNameLabel;
    @FXML
    Label productPriceLabel;
    @FXML
    Label productUnitSuffixLabel;
    @FXML
    Button addButton;
    @FXML
    Button removeButton;
    @FXML
    Label productAmountLabel;
    @FXML
    TextField productAmountTextField;

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
    }
    protected String getCorrectSuffix() {
        String suffix = product.getUnitSuffix();
        if (Objects.equals(suffix, "kg")) {
            return "kg";
        } else {
            return "st";
        }
    }
    protected void incrementAmountLabel() {
        String currentVal = productAmountTextField.getText();
        currentVal = iMatDataModel.removeSuffixes(currentVal);
        double value = Double.parseDouble(currentVal);


    }

    protected void decrementAmountLabel() {
        String currentVal = productAmountTextField.getText();
        currentVal = iMatDataModel.removeSuffixes(currentVal);
        double value = Double.parseDouble(currentVal);
        String suffix = product.getUnitSuffix();
        if (value == 0) {
            return;
        }
        if (Objects.equals(suffix, "kg")) {
            value -= 0.1;
            value = iMatDataModel.round(value, 2);
            productAmountTextField.setText(value + "kg");
        } else {
            value -= 1;
            productAmountTextField.setText((int) value + "st");
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
