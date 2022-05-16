package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.io.IOException;

public class iMatPay extends wizard {
    @FXML private AnchorPane payRootAnchorPane;

    @FXML private Button next2,back2,shoppingCart;

    @FXML private Ellipse selectDateTime;
    public void next2ButtonPressed() {
        navigateTo("iMatDelivery.fxml", payRootAnchorPane);
    }

    public void back2ButtonPressed() {
        navigateTo("iMatSelectDateTime.fxml", payRootAnchorPane);
    }


    public void toSelectDateTime() {
        navigateToSelectDateTime(payRootAnchorPane);
    }





























    /*
    public void next2ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatDelivery.fxml"));
            payRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back2ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatSelectDateTime.fxml"));
            payRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

}

