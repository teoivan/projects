package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class Property implements Rentable, Displayable, Comparable<Property> {
    private int propertyId;
    private String address;
    private String type;
    private int numBedrooms;
    private int rentalRate;
    private Boolean rentable;


    public Property( String address, String type, int numBedrooms, int rentalRate, Boolean rentable) {
        this.address = address;
        this.type = type;
        this.numBedrooms = numBedrooms;
        this.rentalRate = rentalRate;
        this.rentable = rentable;
    }

    public Property() {
        this.propertyId = -1;
        this.address = null;
        this.type = null;
        this.numBedrooms = -1;
        this.rentalRate = -1;
        this.rentable = false;
    }


    @Override
    public Boolean isRentable() {
        return this.rentable;
    }

    @Override
    public int getRentalRate() {
        return rentalRate;
    }

    @Override
    public void displayInfo() {
        System.out.println("-------------------");
        System.out.println("Property Information:");
        System.out.println("ID: " + propertyId);
        System.out.println("Address: " + address);
        System.out.println("Type: " + type);
        System.out.println("Number of Bedrooms: " + numBedrooms);
        System.out.println("Rental Rate: " + rentalRate);
        if (this.rentable)
            System.out.println("THIS PROPERTY IS AVAILABLE");
        else
            System.out.println("PROPERTY NOT AVAILABLE");
        System.out.println("-------------------");
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public void setRentalRate(int rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Boolean getRentable() {
        return rentable;
    }

    public void setRentable(Boolean rentable) {
        this.rentable = rentable;
    }

    @Override
    public int compareTo(Property other) {
        return Integer.compare(this.rentalRate, other.rentalRate);
    }
}
