package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TimeCard extends AnchorPane {

    @FXML Label timeCardLabel;
    @FXML AnchorPane timeCardAnchorPane;
    iMatSelectDateTime parentController;

    public TimeCard(int time, iMatSelectDateTime controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("timeCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;
        int timePlus = time + 1;
        timeCardLabel.setText(time + "-" + timePlus);
    }

    @FXML
    private void handleOnClick() {
        parentController.updateTimeCards(timeCardLabel.getText());
        this.timeCardAnchorPane.setStyle("-fx-background-color: #C0C0C0");
    }
}
