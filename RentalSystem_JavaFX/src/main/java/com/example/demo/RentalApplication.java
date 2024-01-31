package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.List;

public class RentalApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("main_scene.fxml"));
        primaryStage.setTitle("Rental Property System");
        Database.getConnection();
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setOnCloseRequest(this::handleCloseRequest);
        primaryStage.show();
    }

    private void handleCloseRequest(WindowEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("close_alert.fxml"));
            Parent root = loader.load();
            Stage alertStage = new Stage();
            alertStage.setTitle("Exit Confirmation");
            alertStage.setScene(new Scene(root, 322, 190));

            CloseAlert controller = loader.getController();


            controller.setYesButtonHandler(event1 -> {
                // User clicked "YES," close the application
                Database.closeConnection();
                alertStage.close();
            });

            controller.setNoButtonHandler(event1 -> {
                event.consume();
                alertStage.close();
            });

            alertStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}