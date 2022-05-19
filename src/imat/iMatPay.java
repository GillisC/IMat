package imat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.annotation.processing.Filer;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class iMatPay extends wizard {
    @FXML private AnchorPane payRootAnchorPane;

    @FXML private Button next2,back2,shoppingCart;

    @FXML private Ellipse selectDateTime, delivery;
    @FXML private TextField cardNr1,cardNr2,cardNr3,cardNr4,expirationMonth, expirationYear,securityCode;
    String inputNr;
    public void initialize() {

//      https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader("src/imat/resources/database.json"));
            JSONObject pay = (JSONObject) object.get("pay");
            String card1 =  (String) pay.get("cardNumber1");
            cardNr1.setText(card1);

            String card2 =  (String) pay.get("cardNumber2");
            cardNr2.setText(card2);

            String card3 =  (String) pay.get("cardNumber3");
            cardNr3.setText(card3);

            String card4 =  (String) pay.get("cardNumber4");
            cardNr4.setText(card4);

            String month =  (String) pay.get("expirationMonth");
            expirationMonth.setText(month);

            String year =  (String) pay.get("expirationYear");
            expirationYear.setText(year);

            String code =  (String) pay.get("securityCode");
            securityCode.setText(code);

        } catch (Exception e) {

        }
    }


    //data sparas till datbasen när next button trycks
    public void next2ButtonPressed() {
        navigateTo("iMatDelivery.fxml", payRootAnchorPane);
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

























    /*
    public void next2ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatDelivery.fxml"));
            payRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back2ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatSelectDateTime.fxml"));
            payRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

}

