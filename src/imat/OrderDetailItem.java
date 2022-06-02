package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OrderDetailItem extends AnchorPane {

    @FXML private Label itemTitle, itemTotal, itemCategory;
    @FXML private ImageView itemImage;

    IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    ShoppingItem shoppingItem;
    OrderHistory parentController;

    public OrderDetailItem(ShoppingItem item, OrderHistory controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("orderDetailItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.shoppingItem = item;
        this.parentController = controller;
        if (item.getProduct().getUnitSuffix().equals("kg")) {
            itemTitle.setText(iMatDataModel.round(item.getAmount(), 1) + "kg " + item.getProduct().getName());
        } else {
            itemTitle.setText((int) item.getAmount() + "st " + item.getProduct().getName());
        }
        itemCategory.setText(iMatDataModel.getProductCategoryName(item.getProduct().getCategory()));
        itemTotal.setText("Totalt: " + iMatDataModel.round(item.getTotal(), 2) + " kr");
        itemImage.setImage(iMatDataModel.getFXImage(item.getProduct()));
    }
}
