package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class iMatDelivery extends wizard {
    @FXML private AnchorPane deliveryRootAnchorPane;
    @FXML private Button next3,back3,shoppingCart;
    @FXML private Ellipse selectDateTime, pay;
    @FXML private TextField firstN,lastN,road, postNr,phoneNr,mailAddress;


    public void initialize() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader("src/imat/resources/database.json"));
            JSONObject delivery = (JSONObject) object.get("delivery");
            String first =  (String) delivery.get("firstName");
            firstN.setText(first);

            String last =  (String) delivery.get("lastName");
            lastN.setText(last);

            String r =  (String) delivery.get("road");
            road.setText(r);

            String postN =  (String) delivery.get("postNumber");
            postNr.setText(postN);

            String phoneN =  (String) delivery.get("phoneNumber");
            phoneNr.setText(phoneN);

            String mailA =  (String) delivery.get("mailAddress");
            mailAddress.setText(mailA);

        } catch (Exception e) {

        }
    }

    public void next3ButtonPressed() {
        JSONParser parser = new JSONParser();
        JSONObject delivery;
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader("src/imat/resources/database.json"));
            delivery = (JSONObject) object.get("delivery");

            delivery.put("firstName", firstN.getText());
            object.put("delivery", delivery);

            delivery.put("lastName", lastN.getText());
            object.put("delivery", delivery);

            delivery.put("road", road.getText());
            object.put("delivery", delivery);

            delivery.put("postNumber", postNr.getText());
            object.put("delivery", delivery);

            delivery.put("phoneNumber", phoneNr.getText());
            object.put("delivery", delivery);

            delivery.put("mailAddress", mailAddress.getText());
            object.put("delivery", delivery);


            try (FileWriter file = new FileWriter("src/imat/resources/database.json")) {
                file.write(object.toJSONString());
                file.flush();
            }
        } catch (Exception e) {

        }
        navigateTo("iMatPay.fxml", deliveryRootAnchorPane);
    }

    public void back3ButtonPressed() {
        navigateTo("iMatSelectDateTime.fxml", deliveryRootAnchorPane);
    }

    public void toSelectDateTime() {
        navigateToSelectDateTime(deliveryRootAnchorPane);
    }


    public void shoppingCartPressed() {
        shoppingCartPressed (deliveryRootAnchorPane);
    }

























/*    public void next3ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatComplete.fxml"));
            deliveryRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back3ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatPay.fxml"));
            deliveryRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */
}
