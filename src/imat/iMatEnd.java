package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.DayOfWeek;

public class iMatEnd extends wizard {
    @FXML private AnchorPane endRootAnchorPane;
    @FXML private Button continueShopping,shoppingCart ;
    @FXML private Label confirm;

    public void continueButtonPressed() {
        navigateTo("iMat_main.fxml", endRootAnchorPane);
    }

    public void shoppingCartPressed() {
        shoppingCartPressed(endRootAnchorPane);
    }




    @FXML
    private void initialize() {
        confirm.setText("Din bekräftade leverans tid är " + iMatSelectDateTime.selectedDay + " " + iMatSelectDateTime.selectedTime + ". "
                +"Vi kommer att skicka ett mail till din angivna mailadress när leveransen är redo :)");
    }
    //confirmation
}