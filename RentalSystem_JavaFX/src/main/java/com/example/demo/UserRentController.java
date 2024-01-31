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

public class UserRentController {
    @FXML
    Button homeButton, rentButton1, leaseButton;

    @FXML
    Button rentButton;

    @FXML
    private TextField idField, idField1, idField11, idField111;

    @FXML
    private VBox contentVBox;

    @FXML
    private Label messageLabel, messageLabel1, infoLabel, nameLabel, phoneLabel, emailLabel;
    public ScrollPane scrollPane;

    private Property propertyRented;
    private Lease lease;


    PropertyManager propertyManager = new PropertyManager(Database.getProperties());

    @FXML
    private void initialize() {
        rentButton.setVisible(false);
        contentVBox.getChildren().clear();
        infoLabel.setVisible(false);
        idField1.setVisible(false);
        idField11.setVisible(false);
        idField111.setVisible(false);
        leaseButton.setVisible(false);
        nameLabel.setVisible(false);
        phoneLabel.setVisible(false);
        emailLabel.setVisible(false);
        rentButton1.setVisible(false);
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
        Parent root = FXMLLoader.load(getClass().getResource("user_menu.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    private TitledPane createTitledPane(Property property) {
        TitledPane titledPane = new TitledPane();
        titledPane.setText("Property ID: " + property.getPropertyId());
        String available = "AVAILABLE";
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

    public void handleSearchButton(ActionEvent event) {
        contentVBox.getChildren().clear();
        if (idField.getText() == "") {
            messageLabel.setText("THE PROPERTY ID FIELD MUST NOT BE EMPTY");
        }
        try {
            int id = Integer.parseInt(idField.getText());
            Property property = propertyManager.searchProperty(id);

            if (property == null) {
                messageLabel.setText("THE PROPERTY DOESN'T EXIST OR IT'S NOT AVAILABLE");
                rentButton.setVisible(false);
            } else {
                messageLabel.setText("");
                TitledPane titledPane = createTitledPane(property);
                contentVBox.getChildren().add(titledPane);
                propertyRented=property;
                rentButton.setVisible(true);
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("INVALID PROPERTY ID. PLEASE ENTER A VALID INTEGER");
        }

    }

    public void handleRentButton(ActionEvent event) {
        rentButton.setVisible(false);
        infoLabel.setVisible(true);
        nameLabel.setVisible(true);
        phoneLabel.setVisible(true);
        emailLabel.setVisible(true);
        idField1.setVisible(true);
        idField11.setVisible(true);
        idField111.setVisible(true);
        rentButton1.setVisible(true);
    }

    private boolean n,e,p;

    public void handleRentButton1(ActionEvent event) {
        n=true;
        e=true;
        p=true;
        String phoneNumber;
        phoneNumber = idField11.getText();
        if (!phoneNumber.matches("\\d+") || phoneNumber.length() != 10) {
            messageLabel1.setText("Phone number must contain 10 digits");
            p=false;
        }else p=true;
        String name=idField1.getText();
        if(!name.matches("^[a-zA-Z ]+$")) {
            messageLabel1.setText("Invalid name format");
            n=false;
        }else n=true;
        String email=idField111.getText();
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){
                messageLabel1.setText("Invalid email address");
            e=false;
        }else e=true;
        if (n && e && p) {
            System.out.println(6);
            messageLabel1.setText("PROPERTY IS RENTED!");
            Tenant tenant=new Tenant(name,phoneNumber,email);
            Database.addTenant(tenant);
            lease=new Lease(propertyRented,tenant);
            Database.addLease(lease);
            Database.rentProperty(Integer.parseInt(idField.getText()));
            leaseButton.setVisible(true);
        }
    }

    public void handleLeaseButton(ActionEvent event){
        if (lease != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("user_lease.fxml"));
                Parent root = loader.load();

                UserLeaseController leaseController = loader.getController();
                leaseController.initialize(lease);

                Stage leaseInfoStage = new Stage();
                Scene scene = new Scene(root);
                leaseInfoStage.setScene(scene);
                leaseInfoStage.setTitle("Lease Information");
                leaseInfoStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
