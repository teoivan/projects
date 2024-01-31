package com.example.demo;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Button exitButton;

    @FXML
    private Button userButton;

    @FXML
    private Button guestButton;

    @FXML
    private Button adminButton;

    public void buttonOnClick(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Stage stage = null;
        Parent root = null;
        if (clickedButton == guestButton) {
            stage = (Stage) guestButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("guest_scene.fxml"));
        }  else if (clickedButton == exitButton) {
            stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
            Database.closeConnection();
        } else if(clickedButton==adminButton){
            stage = (Stage) adminButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("admin_scene.fxml"));
        }else if(clickedButton==userButton){
            stage = (Stage) userButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("user_scene.fxml"));
        }
        if (clickedButton != exitButton) {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void handleMouseExited(MouseEvent event) {
        Button enteredButton = (Button) event.getSource();
        enteredButton.setStyle("-fx-background-color: #EAD196;");
    }
    @FXML
    private void handleMouseEntered(MouseEvent event) {
        Button releasedButton = (Button) event.getSource();
        releasedButton.setStyle("-fx-background-color: #F3EDC8;");
    }
}