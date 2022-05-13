package imat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class IMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("imat/resources/IMat");
        // Font.loadFont(this.getClass().getResourceAsStream("Sen-Regular.ttf"), 40.0D);

        Parent root = FXMLLoader.load(getClass().getResource("varukorgItem.fxml"), bundle);

        Scene scene = new Scene(root, 1080, 720);
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }


}
