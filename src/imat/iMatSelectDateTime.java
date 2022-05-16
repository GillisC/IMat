package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;

import java.io.IOException;

public class iMatSelectDateTime extends wizard {
    @FXML private AnchorPane dateTimeRootAnchorPane;
    @FXML private Button back1, next1, shoppingCart;
    @FXML
    Ellipse selectDateTime,pay,delivery,complete;
    public void next1ButtonPressed() {
        navigateTo("iMatPay.fxml", dateTimeRootAnchorPane);
    }

    public void back1ButtonPressed() {
        navigateTo("iMatEnd.fxml", dateTimeRootAnchorPane);
    }




















/*    public void next1ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatPay.fxml"));
            dateTimeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        navigateTo("iMatPay.fxml", dateTimeRootAnchorPane);
    }

    public void back1ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatEnd.fxml"));
            dateTimeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        navigateTo("iMatEnd.fxml", dateTimeRootAnchorPane);
    }
*/
}
