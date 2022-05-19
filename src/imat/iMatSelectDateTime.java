package imat;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class iMatSelectDateTime extends wizard {
    @FXML private AnchorPane dateTimeRootAnchorPane;

    @FXML private Label reminder;
    @FXML private Button back1, next1, shoppingCart;
    @FXML private Ellipse selectDateTime,pay,delivery,complete;
    @FXML private Rectangle rectangle1, rectangle2,rectangle3,rectangle4,rectangle5,rectangle6,rectangle7,rectangle8,rectangle9,rectangle10,rectangle11,rectangle12,rectangle13;

    public static String selectedDay = null;
    public static String selectedTime = null;


    @FXML
    private void initialize() {
        rectangle1.setOnMouseClicked(t -> onRectangleClicked(rectangle1, "Måndag den 7 juni"));
        rectangle2.setOnMouseClicked(t -> onRectangleClicked(rectangle2, "Tisday den 8 juni"));
        rectangle3.setOnMouseClicked(t -> onRectangleClicked(rectangle3, "Onsday den 9 juni"));
        rectangle4.setOnMouseClicked(t -> onRectangleClicked(rectangle4, "Tordag den 10 juni"));
        rectangle5.setOnMouseClicked(t -> onRectangleClicked(rectangle5, "Fredag den 11 juni"));
        rectangle6.setOnMouseClicked(t -> onRectangleClicked(rectangle6, "Lördag den 12 juni"));
        rectangle7.setOnMouseClicked(t -> onRectangleClicked(rectangle7, "Söndag den 13 juni"));

        rectangle8.setOnMouseClicked(t -> onTimeClicked(rectangle8, "mellan 9 och 10"));
        rectangle9.setOnMouseClicked(t -> onTimeClicked(rectangle9, "mellan 10 och 11"));
        rectangle10.setOnMouseClicked(t -> onTimeClicked(rectangle10, "mellan 11 och 12"));
        rectangle11.setOnMouseClicked(t -> onTimeClicked(rectangle11, "mellan 14 och 15"));
        rectangle12.setOnMouseClicked(t -> onTimeClicked(rectangle12, "mellan 15 och 16"));
        rectangle13.setOnMouseClicked(t -> onTimeClicked(rectangle13, "mellan 16 och 17"));
    }

    private void onRectangleClicked(Rectangle rectangle, String day) {
        clearRectangleColors();
        rectangle.setFill(Color.rgb(76,92,99));
        selectedDay = day;
    }

    private void onTimeClicked(Rectangle rectangle, String time) {
        clearTimeColors();
        rectangle.setFill(Color.rgb(76,92,99));
        selectedTime = time;
    }

    private void clearRectangleColors () {
        rectangle1.setFill(Color.rgb(243,243,243));
        rectangle2.setFill(Color.rgb(243,243,243));
        rectangle3.setFill(Color.rgb(243,243,243));
        rectangle4.setFill(Color.rgb(243,243,243));
        rectangle5.setFill(Color.rgb(243,243,243));
        rectangle6.setFill(Color.rgb(243,243,243));
        rectangle7.setFill(Color.rgb(243,243,243));
    }
    private void clearTimeColors (){
        rectangle8.setFill(Color.rgb(243,243,243));
        rectangle9.setFill(Color.rgb(243,243,243));
        rectangle10.setFill(Color.rgb(243,243,243));
        rectangle11.setFill(Color.rgb(243,243,243));
        rectangle12.setFill(Color.rgb(243,243,243));
        rectangle13.setFill(Color.rgb(243,243,243));
    }


    public void next1ButtonPressed() {
        if (selectedDay!= null && selectedTime != null){
            navigateTo("iMatDelivery.fxml", dateTimeRootAnchorPane);// fixa ett pop upp fönster för felmeddelande eller liknade
        }
    }

    public void back1ButtonPressed() {
        navigateTo("imat_main.fxml", dateTimeRootAnchorPane);
    }

    public void shoppingCartPressed() {
        shoppingCartPressed (dateTimeRootAnchorPane);
    }






















/*    public void next1ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatPay.fxml"));
            dateTimeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        navigateTo("iMatPay.fxml", dateTimeRootAnchorPane);
    }

    public void back1ButtonPressed() {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("iMatEnd.fxml"));
            dateTimeRootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        navigateTo("iMatEnd.fxml", dateTimeRootAnchorPane);
    }
*/
}
