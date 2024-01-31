package com.example.demo;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class UserSignUpController {
    @FXML
    private Button backButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label usernameLabel, messageLabel,passwordLabel;


    public void handleBackButton(ActionEvent event) throws IOException {

        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("user_scene.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void handleSignupButton(ActionEvent event)throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean validPass=isValidPassword(password);
        boolean validUser=isValidUser(username);
        if(!validUser){
            messageLabel.setText("");
            usernameLabel.setText("Invalid username");
        }
        if(!validPass){
            passwordLabel.setText("Passwords must contain at least 8 letters");
            messageLabel.setText("");
        }
        if (Database.getUserByUsername(username)==null) {
           if(validPass&&validUser){
               Database.addUser(username, password);
               usernameLabel.setText("");
               passwordLabel.setText("");
               messageLabel.setText("Sign up successful. Now you can LOG IN");
           }
        } else {
            usernameLabel.setText("Username already exists");
            messageLabel.setText("");
        }
    }
    private static boolean isValidPassword(String password) {

        return password.matches("^[a-zA-Z0-9_]+$") && password.length() >= 8;
    }
    private static boolean isValidUser(String user) {

        return user.matches("^[a-zA-Z0-9_]+$");
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
