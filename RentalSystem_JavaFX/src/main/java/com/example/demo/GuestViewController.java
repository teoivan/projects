package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GuestViewController {
    @FXML
    Button homeButton;
    PropertyManager propertyManager = new PropertyManager(Database.getProperties());
    List<Property> properties = propertyManager.getProperties();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentVBox;

    @FXML
    private void initialize() {
        for (Property property : properties) {
            TitledPane titledPane = createTitledPane(property);
            contentVBox.getChildren().add(titledPane);
        }
        scrollPane.setContent(contentVBox);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private TitledPane createTitledPane(Property property) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText("Property ID: " + property.getPropertyId());
        String available;
        if (property.getRentable()) {
            available = "AVAILABLE";
        } else {
            available = "NOT AVAILABLE";
        }
        VBox content = new VBox();
        content.setStyle("-fx-background-color: #F3EDC8");
        Font customFont = Font.font("Arial", FontWeight.BOLD, 12);

        content.getChildren().addAll(
                new Label("DETAILS"),
                new Label(""),
                new Label("Type: " + property.getType()),
                new Label("Address: " + property.getAddress()),
                new Label("Number of bedrooms: " + property.getNumBedrooms()),
                new Label("Rate: " + property.getRentalRate() + "$"),
                new Label("Availability: " + available)
        );

        for (Node node : content.getChildren()) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String labelText = label.getText();
                if (labelText.startsWith("Type:") || labelText.startsWith("Address:") || labelText.startsWith("Number of bedrooms:")
                        || labelText.startsWith("Rate:") || labelText.startsWith("Availability:")) {
                    label.setFont(customFont);
                }
            }
        }
        titledPane.setContent(content);

        return titledPane;
    }

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
        Parent root = FXMLLoader.load(getClass().getResource("guest_menu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
