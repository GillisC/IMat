package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;


import java.io.IOException;

public class wizard extends AnchorPane {

    public void navigateTo(String resource, AnchorPane pane){
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource(resource));
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shoppingCartPressed(AnchorPane pane) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatShoppingCart.fxml"));
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void navigateToSelectDateTime(AnchorPane pane) {
        navigateTo("iMatSelectDateTime.fxml", pane);
    }

    public void navigateToPay(AnchorPane pane) {
        navigateTo("iMatPay.fxml", pane);
    }

    public void navigateToDelivery(AnchorPane pane) {
        navigateTo("iMatDelivery.fxml", pane);
    }

}
