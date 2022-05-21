package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Ellipse;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class iMatComplete extends wizard implements ShoppingCartManager {

    @FXML private AnchorPane completeRootAnchorPane;
    @FXML private FlowPane completeFlowPane;
    @FXML private Button complete,back4,shoppingCart;
    @FXML private Ellipse selectDateTime, pay, delivery;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();

    public void initialize() {
        updateFlowPane();
    }


    protected void updateFlowPane() {
        completeFlowPane.getChildren().clear();
        List<ShoppingItem> shoppingItemsList = iMatDataModel.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : shoppingItemsList){
            completeFlowPane.getChildren().add(new VarukorgItem(shoppingItem, this));
        }
    }

    public void handleAddProduct(Product product) {
        iMatDataModel.addToShoppingCart(product);
        updateFlowPane();
    }

    public void handleRemoveProduct(Product product) {
        iMatDataModel.removeFromShoppingCart(product);
        updateFlowPane();
    }
    public void CompleteButtonPressed() {
        navigateTo("iMatEnd.fxml", completeRootAnchorPane);
    }

    public void back4ButtonPressed() {
        navigateTo("iMatDelivery.fxml", completeRootAnchorPane);
    }

    public void toSelectDateTime() {
        navigateToSelectDateTime(completeRootAnchorPane);
    }

    public void toPay() {
        navigateToPay(completeRootAnchorPane);
    }

    public void toDelivery() {
        navigateToDelivery(completeRootAnchorPane);
    }

    public void shoppingCartPressed() {
        shoppingCartPressed (completeRootAnchorPane);
    }



























 /*   public void CompleteButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatEnd.fxml"));
            completeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back4ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatDelivery.fxml"));
            completeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  */
}