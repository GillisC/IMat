package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistory implements Initializable {

    @FXML private FlowPane orderHistoryFlowPane, detailFlowPane;
    @FXML private AnchorPane detailViewAnchorPane, orderHistoryAnchorPane, orderHistoryRootPane;
    @FXML private ImageView closeDetailViewImageView;
    @FXML private Label detailedOrderNum;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    private Order currentOrder = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateOrderHistory();
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
    }

    @FXML
    public void closeButtonExitHover() {
        closeDetailViewImageView.setImage(iMatDataModel.getImageFromUrl("imat/resources/close-button-blue.png"));
    }

    @FXML
    public void handleAddToShoppingCart() {
        for (ShoppingItem item : currentOrder.getItems()) {
            iMatDataModel.getShoppingCart().addItem(item);
        }

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
