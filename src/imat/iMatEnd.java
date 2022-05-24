package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.time.DayOfWeek;

public class iMatEnd extends wizard {
    @FXML private AnchorPane endRootAnchorPane;
    @FXML private Button continueShopping,shoppingCart ;
    @FXML private Label confirmationLabel;


    IMatDataModel iMatDataModel = IMatDataModel.getInstance();

    @FXML
    private void initialize() {
        confirmationLabel.setText("Din order är bekräftad, och kommer att levereras hem till dig den " + iMatDataModel.getSelectedDay() + " mellan: " + iMatDataModel.getSelectedTime() + "!");
    }


    public void continueButtonPressed() {
        navigateTo("iMat_main.fxml", endRootAnchorPane);
    }


    @FXML
    public void clickOnEscapePatch() {
        navigateTo("imat_main.fxml", endRootAnchorPane);
    }

}