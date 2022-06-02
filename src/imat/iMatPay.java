package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import se.chalmers.cse.dat216.project.CreditCard;

import javax.annotation.processing.Filer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class iMatPay extends wizard {
    @FXML private AnchorPane payRootAnchorPane;

    @FXML private Button next2,back2;
    @FXML private TextField cardNum1,cardNum2,cardNum3,cardNum4,expirationMonth, expirationYear,cvcCode;
    @FXML private Rectangle chooseTimeRec, chooseDeliveryRec, choosePayRec, chooseConfirmRec, rec1, rec2, rec3;
    @FXML private Label reminderText;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    CreditCard creditCard;
    String creditCardNum = "";

    public void initialize() {
        creditCard = iMatDataModel.getCreditCard();
        updateTextFields();
        updateStepBackground();
        reminderText.setText("");
        iMatDataModel.setOnHover(next2);
        iMatDataModel.setOnHover(back2);

        cardNum1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!cardNum1.getText().matches("^\\d{4}$")) {
                    cardNum1.setText("");
                }
            }
        });

        cardNum2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!cardNum2.getText().matches("^\\d{4}$")) {
                    cardNum2.setText("");
                }
            }
        });

        cardNum3.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!cardNum3.getText().matches("^\\d{4}$")) {
                    cardNum3.setText("");
                }
            }
        });

        cardNum4.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!cardNum4.getText().matches("^\\d{4}$")) {
                    cardNum4.setText("");
                }
            }
        });

        expirationMonth.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!expirationMonth.getText().matches("^\\d{2}$")) {
                    expirationMonth.setText("");
                }
            }
        });

        expirationYear.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!expirationYear.getText().matches("^\\d{2}$") || Integer.parseInt(expirationYear.getText()) < 22) {
                    expirationYear.setText("");
                }
            }
        });

        cvcCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!cvcCode.getText().matches("^\\d{3}$")) {
                    cvcCode.setText("");
                }
            }
        });

        cardNum1.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                cardNum2.requestFocus();
            }
        });

        cardNum2.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                cardNum3.requestFocus();
            }
        });

        cardNum3.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                cardNum4.requestFocus();
            }
        });

        cardNum4.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                expirationMonth.requestFocus();
            }
        });

        expirationMonth.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                expirationYear.requestFocus();
            }
        });

        expirationYear.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                cvcCode.requestFocus();
            }
        });

        cvcCode.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                next2.requestFocus();
            }
        });
    }

    private void updateTextFields() {
        char[] cardNumArray = creditCard.getCardNumber().toCharArray();
        if (creditCard.getCardNumber().length() == 16) {
            cardNum1.setText(String.valueOf(cardNumArray[0])+String.valueOf(cardNumArray[1])+String.valueOf(cardNumArray[2])+String.valueOf(cardNumArray[3]));
            cardNum2.setText(String.valueOf(cardNumArray[4])+String.valueOf(cardNumArray[5])+String.valueOf(cardNumArray[6])+String.valueOf(cardNumArray[7]));
            cardNum3.setText(String.valueOf(cardNumArray[8])+String.valueOf(cardNumArray[9])+String.valueOf(cardNumArray[10])+String.valueOf(cardNumArray[11]));
            cardNum4.setText(String.valueOf(cardNumArray[12])+String.valueOf(cardNumArray[13])+String.valueOf(cardNumArray[14])+String.valueOf(cardNumArray[15]));
        }

        System.out.println(creditCard.getCardNumber());

        expirationMonth.setText(String.valueOf(creditCard.getValidMonth()));
        expirationYear.setText(String.valueOf(creditCard.getValidYear()));

        cvcCode.setText(String.valueOf(creditCard.getVerificationCode()));
    }

    private void updatePaymentDetails() {
        creditCard.setCardNumber((cardNum1.getText() + cardNum2.getText() + cardNum3.getText() + cardNum4.getText()).replace(" ", ""));
        if (creditCard.getCardNumber().charAt(0) == '4') {
            creditCard.setCardType("Visa"); // Credit card number beginning with 4 = visa, 5 = mastercard
        } else {
            creditCard.setCardType("Mastercard");
        }
        creditCard.setValidMonth(Integer.parseInt(expirationMonth.getText()));
        creditCard.setValidYear(Integer.parseInt(expirationYear.getText()));
        creditCard.setVerificationCode(Integer.parseInt(cvcCode.getText()));
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


    private boolean isCellsEmpty() {
        return (cardNum1.getText().isEmpty() || cardNum2.getText().isEmpty() || cardNum3.getText().isEmpty() || cardNum4.getText().isEmpty() || expirationMonth.getText().isEmpty() || expirationYear.getText().isEmpty() || cvcCode.getText().isEmpty());
    }
    //data sparas till datbasen när next button trycks
    public void next2ButtonPressed() {
        if (!isCellsEmpty()) {
            updatePaymentDetails();
            iMatDataModel.selectPayComplete = true;
            navigateTo("iMatComplete.fxml", payRootAnchorPane);
        } else {
            reminderText.setText("Var vänlig och fyll i samtliga uppgifter");
        }

    }

    public void back2ButtonPressed() {
        navigateTo("iMatDelivery.fxml", payRootAnchorPane);
    }

    public void toSelectDateTime() {
        navigateToSelectDateTime(payRootAnchorPane);
    }

    public void toDelivery() {
        navigateToDelivery(payRootAnchorPane);
    }

    public void shoppingCartPressed() {
        shoppingCartPressed (payRootAnchorPane);
    }

    @FXML
    public void clickOnEscapePatch() {
        navigateTo("imat_main.fxml", payRootAnchorPane);
    }
}

