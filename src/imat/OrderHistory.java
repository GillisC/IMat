package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistory implements Initializable {

    @FXML private FlowPane orderHistoryFlowPane, detailFlowPane;
    @FXML private AnchorPane detailViewAnchorPane, orderHistoryAnchorPane, orderHistoryRootPane;
    @FXML private ImageView closeDetailViewImageView, backArrow;
    @FXML private Label detailedOrderNum, escapeHatch, alertText;
    @FXML private Button addOrderToCartButton;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    private Order currentOrder = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateOrderHistory();

        iMatDataModel.setOnHover(addOrderToCartButton);
        iMatDataModel.setOnHover(escapeHatch);

        alertText.setText("");
    }

    private void populateOrderHistory() {
        orderHistoryFlowPane.getChildren().clear();
        for (Order order : iMatDataModel.getOrders()) {
            orderHistoryFlowPane.getChildren().add(new OrderItem(order, this));
        }
    }

    protected void populateDetailView(Order order) {
        detailFlowPane.getChildren().clear();
        for (ShoppingItem item : order.getItems()) {
            detailFlowPane.getChildren().add(new OrderDetailItem(item, this));
        }
        detailedOrderNum.setText("Order: #" + order.getOrderNumber());
        currentOrder = order;
    }

    protected void openDetailView(Order order) {
        populateDetailView(order);
        detailViewAnchorPane.toFront();
    }

    @FXML
    public void closeDetailView() {
        orderHistoryAnchorPane.toFront();
    }

    @FXML
    public void closeButtonHover() {
        closeDetailViewImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-blue-hover.png"));
        closeDetailViewImageView.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void closeButtonExitHover() {
        closeDetailViewImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-blue.png"));
        closeDetailViewImageView.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void backArrowHover() {
        backArrow.setImage(iMatDataModel.getImageFromUrl("imat/resources/arrow-white-hover.png"));
        backArrow.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    public void backArrowExitHover() {
        backArrow.setImage(iMatDataModel.getImageFromUrl("imat/resources/arrow-white.png"));
        backArrow.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    public void backArrowAction() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("imat_main.fxml"));
            orderHistoryRootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddToShoppingCart() {
        for (ShoppingItem item : currentOrder.getItems()) {
            iMatDataModel.getShoppingCart().addItem(item);
        }
        alertText.setText("Varorna har lagts till!");
    }

    @FXML
    public void clickOnEscapePatch() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("imat_main.fxml"));
            orderHistoryRootPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
