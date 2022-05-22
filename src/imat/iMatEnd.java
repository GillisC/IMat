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

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();

    @FXML
    private void initialize() {

    }

    public void continueButtonPressed() {
        navigateTo("iMat_main.fxml", endRootAnchorPane);
    }

    public void shoppingCartPressed() {
        shoppingCartPressed(endRootAnchorPane);
    }



}