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

public class GuestMenuController {
    @FXML
    private Button exitButton;

    @FXML
    private Button backButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button filteredButton;

    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("guest_scene.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void buttonOnClick(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Stage stage = null;
        Parent root = null;
        if (clickedButton == viewButton) {
            stage = (Stage) viewButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("guest_view.fxml"));
        }  else if (clickedButton == exitButton) {
            stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
            Database.closeConnection();
        } else if(clickedButton==filteredButton){
            stage = (Stage) viewButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("guest_filtered.fxml"));
        }
        if (clickedButton != exitButton) {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void handleMouseEntered(MouseEvent event) {
        Button enteredButton = (Button) event.getSource();
        enteredButton.setStyle("-fx-background-color: #EAD196;");
    }
    @FXML
    private void handleMouseExited(MouseEvent event) {
        Button releasedButton = (Button) event.getSource();
        releasedButton.setStyle("-fx-background-color: #F3EDC8;");
    }
}
