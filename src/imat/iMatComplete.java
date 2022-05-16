package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;

import java.io.IOException;

public class iMatComplete  extends wizard {
    @FXML private AnchorPane completeRootAnchorPane;
    @FXML private Button complete,back4,shoppingCart;
    @FXML private Ellipse selectDateTime, pay, delivery;

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