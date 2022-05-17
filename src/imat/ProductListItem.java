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
        productAmountTextField.setText("0");
    }

    private void incrementAmountLabel() {
        double currentVal = Double.parseDouble(productAmountTextField.getText());
        String suffix = product.getUnitSuffix();
        if (Objects.equals(suffix, "kg")) {
            currentVal += 0.1;
            currentVal = round(currentVal, 2);
        } else {
            currentVal += 1;
            currentVal = (int) currentVal;
        }
        productAmountTextField.setText(String.valueOf(currentVal));
    }

    private void decrementAmountLabel() {
        double currentVal = Double.parseDouble(productAmountTextField.getText());
        String suffix = product.getUnitSuffix();
        if (currentVal == 0) {
            return;
        }
        if (Objects.equals(suffix, "kg")) {
            currentVal -= 0.1;
            currentVal = round(currentVal, 2);
        } else {
            currentVal -= 1;
            currentVal = (int) currentVal;
        }
        productAmountTextField.setText(String.valueOf(currentVal));
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        System.out.println("Added: " + product.getName() + " to the Shoppingcart" + product.getUnitSuffix());
        incrementAmountLabel();
    }

    @FXML
    private void handleRemoveAction(ActionEvent event) {
        System.out.println("Removed: " + product.getName() + " from the Shoppingcart");
        decrementAmountLabel();
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    protected ProductCategory getProductListItemCategory() {
        return product.getCategory();
    }
    protected String getProductListItemName() {
        return product.getName();
    }
}
