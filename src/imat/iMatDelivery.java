package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;

import java.io.IOException;

public class iMatDelivery extends wizard {
    @FXML private AnchorPane deliveryRootAnchorPane;
    @FXML private Button next3,back3,shoppingCart;
    @FXML private Ellipse selectDateTime, pay;

    public void next3ButtonPressed() {
        navigateTo("iMatComplete.fxml", deliveryRootAnchorPane);
    }

    public void back3ButtonPressed() {
        navigateTo("iMatPay.fxml", deliveryRootAnchorPane);
    }
    public void toSelectDateTime() {
        navigateToSelectDateTime(deliveryRootAnchorPane);
    }
    public void toPay() {
        navigateToPay(deliveryRootAnchorPane);
    }
























/*    public void next3ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatComplete.fxml"));
            deliveryRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back3ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatPay.fxml"));
            deliveryRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */
}
