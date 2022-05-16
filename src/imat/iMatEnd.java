package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class iMatEnd extends wizard {
    @FXML private AnchorPane endRootAnchorPane;
    @FXML private Button continueShopping;
    public void continueButtonPressed() {
        navigateTo("iMatSelectDateTime.fxml", endRootAnchorPane);
    }

/*    public void continueButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatSelectDateTime.fxml"));
            endRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 */

}
