package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SubCategoryItem extends AnchorPane {

    @FXML Label subCategoryItemLabel;
    @FXML AnchorPane backdropAnchorPane;
    IMatDataModel iMatDataModel = IMatDataModel.getInstance();

    String subCategoryName;
    IMatController parentController;

    public SubCategoryItem(String subCategoryName, IMatController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("subCategoryItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("yo");
            throw new RuntimeException(exception);
        }

        this.subCategoryName = subCategoryName;
        this.parentController = controller;

        subCategoryItemLabel.setText(subCategoryName);

        iMatDataModel.setOnHover(backdropAnchorPane);
        }

    @FXML
    private void handleOnClick() {
        System.out.println(subCategoryName);
        parentController.updateProductGridWithSub(subCategoryName);
        this.backdropAnchorPane.setStyle("-fx-background-color: #C0C0C0");
    }
}
