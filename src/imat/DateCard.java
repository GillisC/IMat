package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;

public class DateCard extends AnchorPane {

    @FXML Label dateCardDayLabel;
    @FXML Label dateCardMonthLabel;
    @FXML AnchorPane dayCardAnchorPane;

    iMatSelectDateTime parentController;

    public DateCard(String[] dayList, iMatSelectDateTime controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dateCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = controller;
        dateCardDayLabel.setText(dayList[0]);
        dateCardMonthLabel.setText(dayList[2] + " " + dayList[1]);
    }

    @FXML
    private void handleOnClick() {
        parentController.updateDayCards(dateCardMonthLabel.getText());
        this.dayCardAnchorPane.setStyle("-fx-background-color: #C0C0C0");
    }

}
