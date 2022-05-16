package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class CategoryItem extends AnchorPane {

    @FXML ImageView categoryItemImageView;
    @FXML Label categoryItemLabel;
    IMatDataModel iMatDataModel = IMatDataModel.getInstance();

    String name;

    public CategoryItem(String categoryName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("categoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("yo");
            throw new RuntimeException(exception);
        }

        this.name = categoryName;

        Image image = iMatDataModel.getImageFromUrl(String.format("imat/resources/icons/%s-deselected.png", name));
        categoryItemImageView.setImage(image);
        categoryItemLabel.setText(name);
    }

}
