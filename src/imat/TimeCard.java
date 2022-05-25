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

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();

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

        iMatDataModel.setOnHover(timeCardAnchorPane);
    }

    @FXML
    private void handleOnClick() {
        iMatDataModel.setSelectedTime(timeCardLabel.getText());
        parentController.updateTimeCards();
    }
}
