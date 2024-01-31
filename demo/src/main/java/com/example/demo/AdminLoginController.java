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

public class AdminLoginController {
    @FXML
    private Button backButton;


    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    public void initialize() {
        errorLabel.prefWidthProperty().bind(Bindings.createDoubleBinding(() -> {
            Text text = new Text(errorLabel.getText());
            text.setFont(errorLabel.getFont());
            return text.getLayoutBounds().getWidth();
        }, errorLabel.textProperty()));
    }

    public void handleBackButton(ActionEvent event) throws IOException {

        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("admin_scene.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void handleLoginButton(ActionEvent event)throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (Database.authenticateAdmin(username, password)) {
            Parent root;
            Stage stage;
            stage = (Stage) loginButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("admin_menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText(" Invalid admin credentials. Try again! ");
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
