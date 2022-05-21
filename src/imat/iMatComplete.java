package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Ellipse;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class iMatComplete extends wizard implements ShoppingCartManager {

    @FXML private AnchorPane completeRootAnchorPane;
    @FXML private FlowPane completeFlowPane;
    @FXML private Button complete,back4, changeDelivery, changePay;
    @FXML private Label fullNameLabel, emailLabel, addressPostNumLabel, phoneNumLabel, cardNumLabel, expirationLabel, securityCode;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    Customer customer;
    CreditCard creditCard;

    public void initialize() {
        customer = iMatDataModel.getCustomer();
        creditCard = iMatDataModel.getCreditCard();

        updateFlowPane();

        fullNameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        emailLabel.setText(customer.getEmail());
        addressPostNumLabel.setText(customer.getAddress() + ", " + customer.getPostCode());
        phoneNumLabel.setText(customer.getMobilePhoneNumber());

        cardNumLabel.setText(creditCard.getCardNumber());
        expirationLabel.setText(creditCard.getValidMonth() + "/" + creditCard.getValidYear());
        securityCode.setText(firstDigit(creditCard.getVerificationCode()) + "xx");
    }


    protected void updateFlowPane() {
        completeFlowPane.getChildren().clear();
        List<ShoppingItem> shoppingItemsList = iMatDataModel.getShoppingCart().getItems();
        for (ShoppingItem shoppingItem : shoppingItemsList){
            completeFlowPane.getChildren().add(new VarukorgItem(shoppingItem, this));
        }
    }

    public void handleAddProduct(Product product) {
        iMatDataModel.addToShoppingCart(product);
        updateFlowPane();
    }

    public void handleRemoveProduct(Product product) {
        iMatDataModel.removeFromShoppingCart(product);
        updateFlowPane();
    }

    public void CompleteButtonPressed() {
        iMatDataModel.placeOrder(true);
        navigateTo("iMatEnd.fxml", completeRootAnchorPane);
    }

    public void back4ButtonPressed() {
        navigateTo("iMatDelivery.fxml", completeRootAnchorPane);
    }

    public void toSelectDateTime() {
        navigateToSelectDateTime(completeRootAnchorPane);
    }

    public void toPay() {
        navigateToPay(completeRootAnchorPane);
    }

    public void toDelivery() {
        navigateToDelivery(completeRootAnchorPane);
    }

    public void shoppingCartPressed() {
        shoppingCartPressed (completeRootAnchorPane);
    }

    public static int firstDigit(int n) {
        while (n < -9 || 9 < n) n /= 10;
        return Math.abs(n);
    }
}