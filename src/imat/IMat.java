package imat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class IMat extends Application {

    public static IMatDataModel iMatDataModel = IMatDataModel.getInstance();
    public static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("imat/resources/IMat");

        Parent root = FXMLLoader.load(getClass().getResource("imat_main.fxml"), bundle);

        scene = new Scene(root, 1080, 720);
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                iMatDataModel.shutDown();
            }
        }));
    }

    public Scene getScene() {
        return scene;
    }

}
