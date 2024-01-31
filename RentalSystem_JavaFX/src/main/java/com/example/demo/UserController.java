package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController {

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;


    public void handleBackButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("main_scene.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void buttonOnClick(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        Stage stage = null;
        Parent root = null;
        if (clickedButton == loginButton) {
            stage = (Stage) loginButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("user_login.fxml"));
        } else if (clickedButton == signupButton) {
            stage = (Stage) loginButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("user_signup.fxml"));
        } else if (clickedButton == exitButton) {
            stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
            Database.closeConnection();
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