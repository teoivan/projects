package com.example.demo;

public class Tenant implements Displayable, Comparable<Tenant>{

    private int id;
    private String name;
    private String phoneNumber, email;

    public Tenant(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Tenant() {
        this.name = null;
        this.phoneNumber=null;
        this.email=null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(Tenant other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public void displayInfo() {
        System.out.println("-------------");
            System.out.println("Tenant Information:");
            System.out.println("Name: " + name);
            System.out.println("Phone Number: " + phoneNumber);
            System.out.println("Email: " + email);
        System.out.println("-------------");

    }
}
