package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Objects;

public class VarukorgItem extends AnchorPane{

    private IMatController parentController;
    private ShoppingItem shoppingItem;
    private IMatDataModel dataModel = IMatDataModel.getInstance();

    @FXML ImageView shoppingItemImageView;
    @FXML Label shoppingItemNameLabel;
    @FXML Label shoppingItemUnitLabel;
    @FXML Button shoppingCartItemAddButton;
    @FXML Button shoppingCartItemRemoveButton;
    @FXML Label shoppingItemAmountLabel;
    @FXML Label shoppingItemTotalLabel;

    public VarukorgItem(ShoppingItem shoppingItem, IMatController controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("varukorgItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;
        this.shoppingItem = shoppingItem;

        shoppingItemImageView.setImage(dataModel.getFXImage(shoppingItem.getProduct(), 80, 80));
        shoppingItemNameLabel.setText(shoppingItem.getProduct().getName());
        shoppingItemUnitLabel.setText(shoppingItem.getProduct().getPrice() + (shoppingItem.getProduct().getUnit()));
        String unit = shoppingItem.getProduct().getUnitSuffix();
        if (Objects.equals(unit, "förp")) {
            unit = "st";
        }
        if (Objects.equals(shoppingItem.getProduct().getUnitSuffix(), "kg")) {
            shoppingItemAmountLabel.setText((dataModel.round(shoppingItem.getAmount(), 1) + unit));
        } else {
            shoppingItemAmountLabel.setText((int) shoppingItem.getAmount() + unit);
        }

        shoppingItemTotalLabel.setText(dataModel.round(shoppingItem.getTotal(), 2) + " kr");
    }

    @FXML
    protected void handleAddAction(){parentController.handleAddProduct(shoppingItem.getProduct());}
    @FXML
    protected void handleRemoveAction() {parentController.handleRemoveProduct(shoppingItem.getProduct());}

}
