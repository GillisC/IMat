package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import se.chalmers.cse.dat216.project.Customer;

import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.UnaryOperator;

public class iMatDelivery extends wizard {
    @FXML private AnchorPane deliveryRootAnchorPane;
    @FXML private Button next3,back3,shoppingCart;
    @FXML private TextField firstName,lastName, streetName, zipCode, phoneNum, mailAddress;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    Customer customer;

    public void initialize() {
        customer = iMatDataModel.getCustomer();

        firstName.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                lastName.requestFocus();
            }
        });

        firstName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!firstName.getText().matches("[a-zA-ZåäöÅÄÖ ]+")) {
                    firstName.setText("");
                }
            }
        });

        lastName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!lastName.getText().matches("[a-zA-ZåäöÅÄÖ ]+")) {
                    lastName.setText("");
                }
            }
        });

        lastName.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                streetName.requestFocus();
            }
        });

        streetName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!streetName.getText().matches("^[a-zA-ZåäöÅÄÖ]+(?:\\s\\d+)?$")) {
                    streetName.setText("");
                }
            }
        });

        streetName.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                zipCode.requestFocus();
            }
        });

        zipCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!zipCode.getText().matches("^[0-9 ]+$")) {
                    zipCode.setText("");
                }
            }
        });

        zipCode.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                phoneNum.requestFocus();
            }
        });

        phoneNum.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!phoneNum.getText().matches("^[0-9- ]+$")) {
                    phoneNum.setText("");
                }
            }
        });

        phoneNum.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                mailAddress.requestFocus();
            }
        });

        mailAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!mailAddress.getText().matches("^[a-zA-ZåäöÅÄÖ0-9_!#$%&'*+/=?`{|}~^-]+(?:\\."
                        + "[a-zA-ZåäöÅÄÖ0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-ZåäöÅÄÖ0-9-]+(?:\\.[a-zA-ZåäöÅÄÖ0-9-]+)*$")) {
                    mailAddress.setText("");
                }
            }
        });

        mailAddress.setOnKeyReleased(KeyEvent -> {
            if (KeyEvent.getCode() == KeyCode.ENTER) {
                next3.requestFocus();
            }
        });
    }

    private void updateDeliveryDetails() {
        customer.setFirstName(firstName.getText().replace(" ", ""));
        customer.setLastName(lastName.getText().replace(" ", ""));
        customer.setAddress(streetName.getText());
        customer.setPostAddress(streetName.getText());
        customer.setPostCode(zipCode.getText().replace(" ", ""));
        customer.setMobilePhoneNumber(phoneNum.getText().replace(" ", "").replace("-", ""));
        customer.setEmail(mailAddress.getText().replace(" ", ""));

        System.out.println("Customer is complete: " + iMatDataModel.isCustomerComplete());
    }

    private boolean isCellsEmpty() {
        return (firstName.getText().isEmpty() || lastName.getText().isEmpty() || streetName.getText().isEmpty() || zipCode.getText().isEmpty() || phoneNum.getText().isEmpty() || mailAddress.getText().isEmpty());
    }

    public void next3ButtonPressed() {
        if (!isCellsEmpty()) {
            updateDeliveryDetails();
            navigateTo("iMatPay.fxml", deliveryRootAnchorPane);
        }


        /*JSONParser parser = new JSONParser();
        JSONObject delivery;
        try {
            JSONObject object = (JSONObject) parser.parse(new FileReader("src/imat/resources/database.json"));
            delivery = (JSONObject) object.get("delivery");

         *//*   delivery.put("firstName", firstN.getText());
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
            object.put("delivery", delivery);*//*


            try (FileWriter file = new FileWriter("src/imat/resources/database.json")) {
                file.write(object.toJSONString());
                file.flush();
            }
        } catch (Exception e) {

        }*/
    }

    public void back3ButtonPressed() {
        navigateTo("iMatSelectDateTime.fxml", deliveryRootAnchorPane);
    }

    public void toSelectDateTime() {
        navigateToSelectDateTime(deliveryRootAnchorPane);
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
