package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class OrderItem extends AnchorPane {

    @FXML private Label orderNum, orderDate, orderTotal;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    OrderHistory parentController;
    Order order;

    public OrderItem(Order order, OrderHistory controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.order = order;
        this.parentController = controller;

        orderNum.setText("Order: #" + order.getOrderNumber());
        orderDate.setText("Datum: " + order.getDate());
        orderTotal.setText("Totalt: " + getOrderTotal(order) + " kr");
    }

    public int getOrderTotal(Order order) {
        int total = 0;
        for (ShoppingItem item : order.getItems()) {
            total += item.getTotal();
        }
        return total;
    }

    @FXML
    public void handleOnClick() {
        parentController.openDetailView(order);
    }



}
