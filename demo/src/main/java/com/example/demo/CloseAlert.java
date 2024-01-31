package com.example.demo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CloseAlert {
    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    private EventHandler<ActionEvent> yesButtonHandler;
    private EventHandler<ActionEvent> noButtonHandler;

    public void setYesButtonHandler(EventHandler<ActionEvent> handler) {
        this.yesButtonHandler = handler;
        yesButton.setOnAction(handler);
    }

    public void setNoButtonHandler(EventHandler<ActionEvent> handler) {
        this.noButtonHandler = handler;
        noButton.setOnAction(handler);
    }
}
