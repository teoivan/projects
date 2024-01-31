package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminAddController {
    @FXML
    private Button homeButton;

    @FXML
    private TextField addressField, typeField, bedroomsField, rateField;
    @FXML
    private Label addressLabel, typeLabel, bedroomsLabel, rateLabel, messageLabel;

    List<Property> properties=Database.getProperties();
    PropertyManager propertyManager=new PropertyManager(properties);

    @FXML
    private void onMouseEntered(MouseEvent event) {
        Button enteredButton = (Button) event.getSource();
        enteredButton.setStyle("-fx-background-color: #F3EDC8;");
    }

    @FXML
    private void onMouseExited(MouseEvent event) {
        Button releasedButton = (Button) event.getSource();
        releasedButton.setStyle("-fx-background-color: #EAD196;");
    }

    public void buttonOnClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("admin_menu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void handleAddButton(ActionEvent event) {
        String address = addressField.getText();
        String type = typeField.getText();

        if (!isValidAddress(address)) {
            addressLabel.setText("Invalid address. Please enter a valid address.");
            return;
        }

        if (!isValidType(type)) {
            typeLabel.setText("Invalid property type.");
            return;
        }

        try {
            int bedrooms = Integer.parseInt(bedroomsField.getText());
            int rate = Integer.parseInt(rateField.getText());

            if (!isValidBedrooms(bedrooms)) {
                bedroomsLabel.setText("Invalid number of bedrooms.");
                return;
            }

            if (!isValidRate(rate)) {
                rateLabel.setText("Invalid rate.");
                return;
            }

            Property newProperty = new Property(address, type, bedrooms, rate, true);
            try {
                propertyManager.addProperty(newProperty);
            }catch(DuplicatePropertyException e){
                addressLabel.setText("Property with the same address already exists!");
                return;
            }
            Database.addProperty(newProperty);
            addressLabel.setText("");
            typeLabel.setText("");
            bedroomsLabel.setText("");
            rateLabel.setText("");
            messageLabel.setText("Property added successfully!");
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid numbers for bedrooms and rate.");
        }
    }

    private boolean isValidAddress(String address) {
        return !address.isEmpty() && address.matches("^[a-zA-Z0-9\\s.,#'-]+$");
    }

    private boolean isValidType(String type) {
        return !type.isEmpty() && type.matches("^[a-zA-Z\\s]+$");
    }

    private boolean isValidBedrooms(int bedrooms) {
        return bedrooms > 0;
    }

    private boolean isValidRate(int rate) {
        return rate > 0;
    }
}
