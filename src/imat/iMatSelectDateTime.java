package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class iMatSelectDateTime extends wizard {
    @FXML private HBox dayCardHBox;
    @FXML private HBox timeCardHBox;
    @FXML private AnchorPane dateTimeRootAnchorPane;
    @FXML private Label reminderText;
    @FXML private Button toCartButton, next1;
    @FXML private Ellipse selectDateTime,pay,delivery,complete;
    @FXML private Rectangle chooseTimeRec, chooseDeliveryRec, choosePayRec, chooseConfirmRec, rec1, rec2, rec3;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    ArrayList<DateCard> dateCards = new ArrayList<>();
    ArrayList<TimeCard> timeCards = new ArrayList<>();


    @FXML
    private void initialize() {
        dayCardHBox.setSpacing(10);
        timeCardHBox.setSpacing(10);
        populateDayHBox();
        populateTimeHBox();
        updateDayCards();
        updateTimeCards();

        iMatDataModel.setOnHover(toCartButton);
        iMatDataModel.setOnHover(next1);
        updateStepBackground();

        reminderText.setText("");
    }

    private void populateDayHBox() {
        dayCardHBox.getChildren().clear();
        for (String[] weekday : generateWeekdays()) {
            DateCard dateCard = new DateCard(weekday, this);
            dayCardHBox.getChildren().add(dateCard);
            dateCards.add(dateCard);
        }
    }
    private void populateTimeHBox() {
        timeCardHBox.getChildren().clear();
        for (int i = 9; i < 17; i++) {
            if (i == 12) {
                i += 2;
            }
            TimeCard timeCard =  new TimeCard(i, this);
            timeCardHBox.getChildren().add(timeCard);
            timeCards.add(timeCard);
        }
    }

    private void updateStepBackground() {
        if (iMatDataModel.selectTimeComplete) {
            chooseTimeRec.setStyle("-fx-fill: #C2EABD");
            if (iMatDataModel.selectDeliveryComplete) {
                rec1.setStyle("-fx-fill: #C2EABD");
            }
        }
        if (iMatDataModel.selectDeliveryComplete) {
            chooseDeliveryRec.setStyle("-fx-fill: #C2EABD");
            if (iMatDataModel.selectPayComplete) {
                rec2.setStyle("-fx-fill: #C2EABD");
            }
        }
        System.out.println("check: " + iMatDataModel.isCreditCardComplete());
        if (iMatDataModel.selectPayComplete) {
            choosePayRec.setStyle("-fx-fill: #C2EABD");
        }
    }

    /* index 0: Day, index 1: Month, index 2: dayNumber */
    public ArrayList<String[]> generateWeekdays() {
        ArrayList<String[]> next7days = new ArrayList<>();
        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        for (int i = 0; i < 7; i++) {
            LocalDateTime newLocalDateTime = localDateTime.plusDays(i);
            Date nextDate = Date.from(newLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

            String date = nextDate.toString().replace(" ", "");
            String[] format = {getDaySwe(date.substring(0, 3)), getMonthSwe(date.substring(3, 6)), date.substring(6, 8)};
            next7days.add(format);
        }
        return next7days;
    }

    private String getDaySwe(String dayEng) {
        return switch (dayEng) {
            case "Mon" -> "Mån";
            case "Tue" -> "Tis";
            case "Wed" -> "Ons";
            case "Thu" -> "Tors";
            case "Fri" -> "Fre";
            case "Sat" -> "Lör";
            case "Sun" -> "Sön";
            default -> null;
        };
    }

    private String getMonthSwe(String monthEng) {
        return switch (monthEng) {
            case "Jan" -> "Jan";
            case "Feb" -> "Feb";
            case "Mar" -> "Mar";
            case "Apr" -> "Apr";
            case "May" -> "Maj";
            case "Jun" -> "Jun";
            case "Jul" -> "Juli";
            case "Aug" -> "Aug";
            case "Sep" -> "Sep";
            case "Oct" -> "Okt";
            case "Nov" -> "Nov";
            case "Dec" -> "Dec";
            default -> null;
        };
    }

    protected void updateDayCards() {
        for (DateCard dateCard : dateCards) {
            if (Objects.equals(dateCard.dateCardMonthLabel.getText(), iMatDataModel.getSelectedDay())) {
                dateCard.dayCardAnchorPane.setStyle(
                        "-fx-background-color: #C0C0C0;" +
                                "-fx-border-color: #ED254E;" +
                                "-fx-border-width: 4px;" +
                                "-fx-border-radius: 8px;"
                );
            } else {
                dateCard.dayCardAnchorPane.setStyle("-fx-background-color: #E3E3E3");
            }
        }
    }

    protected void updateTimeCards() {
        for (TimeCard timeCard : timeCards) {
            if (Objects.equals(timeCard.timeCardLabel.getText(), iMatDataModel.getSelectedTime())) {
                timeCard.timeCardAnchorPane.setStyle(
                        "-fx-background-color: #C0C0C0;" +
                        "-fx-border-color: #ED254E;" +
                        "-fx-border-width: 4px;" +
                        "-fx-border-radius: 8px"
                );
            } else {
                timeCard.timeCardAnchorPane.setStyle("-fx-background-color: #E3E3E3");
            }
        }
    }


    public void next1ButtonPressed() {
        System.out.println(iMatDataModel.getDeliveryTime());
        if (iMatDataModel.getSelectedDay()!= null && iMatDataModel.getSelectedTime() != null){
            iMatDataModel.selectTimeComplete = true;
            navigateTo("iMatDelivery.fxml", dateTimeRootAnchorPane);// fixa ett pop upp fönster för felmeddelande eller liknade
        } else {
            reminderText.setText("Var vänlig och välj en dag & tid");
        }
    }

    public void back1ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("imat/imat_main_shoppingcart.fxml"));
            dateTimeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickOnEscapePatch() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("imat/imat_main.fxml"));
            dateTimeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
