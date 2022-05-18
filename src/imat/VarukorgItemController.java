package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class VarukorgItemController extends AnchorPane{
    private IMatDataModel dataModel;
    private ShoppingItem shoppingItem;
    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label productCategory;
    @FXML private ImageView addToCartButton;
    @FXML private ImageView removeFromCartButton;
    @FXML private Label antalLabel;
    @FXML private Label productPrice;

    public VarukorgItemController(IMatDataModel dataModel, ShoppingItem shoppingItem){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("varukorgItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);




        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.dataModel = dataModel;
        this.shoppingItem = shoppingItem;

        productImage.setImage(dataModel.getFXImage(shoppingItem.getProduct()));
        productName.setText(shoppingItem.getProduct().getName());
        productCategory.setText(String.valueOf(shoppingItem.getProduct().getCategory()));
        antalLabel.setText(String.valueOf(shoppingItem.getAmount()) + shoppingItem.getProduct().getUnit());
        productPrice.setText(shoppingItem.getProduct().getPrice() + shoppingItem.getProduct().getUnit());
    }

    @FXML
    protected void hoverAddToCart(){
        addToCartButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/Add to cart buttonhover")));
    }
    @FXML
    protected void hoverRemoveFromCart(){
        removeFromCartButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/Add to cart buttonhover-1")));
    }
    @FXML
    protected void exitedAddToCart(){
        addToCartButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/Add to cart buttonhover+")));
    }
    @FXML
    protected void exitedRemoveFromCart(){
        removeFromCartButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                "imat/resources/Add to cart buttonhover")));
    }
    @FXML
    protected void clickedAddToCart(){
        dataModel.getShoppingCart().addItem(shoppingItem);
    }
    @FXML
    protected void clickedRemoveFromCart(){
        dataModel.getShoppingCart().removeItem(shoppingItem);
    }

}
