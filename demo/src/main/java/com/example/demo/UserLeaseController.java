package com.example.demo;

import javafx.scene.control.Label;

public class UserLeaseController {

    public Label phoneLabel, nameLabel, emailLabel, addressLabel, typeLabel, bedroomsLabel, rateLabel;
    public void initialize(Lease lease) {
        phoneLabel.setText(lease.getTenant().getPhoneNumber());
        nameLabel.setText(lease.getTenant().getName());
        emailLabel.setText(lease.getTenant().getEmail());
        addressLabel.setText(lease.getProperty().getAddress());
        typeLabel.setText(lease.getProperty().getType());
        bedroomsLabel.setText(""+lease.getProperty().getNumBedrooms());
        rateLabel.setText(lease.getProperty().getRentalRate()+"$/month");
    }
}
