package imat;

import javafx.event.ActionEvent;
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
    IMatController parentController;

    public CategoryItem(String categoryName, String selected, IMatController controller) {
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
        this.parentController = controller;
        Image image;

        if (!selected.equals(name)) {
            image = iMatDataModel.getImageFromUrl(String.format("imat/resources/icons/%s-deselected.png", name));
        } else {
            image = iMatDataModel.getImageFromUrl(String.format("imat/resources/icons/%s.png", name));
        }

        categoryItemImageView.setImage(image);
        categoryItemLabel.setText(name);
    }

    @FXML
    private void handleOnClick() {
        System.out.println(name);
        parentController.setMainCategory(name);
    }

}
