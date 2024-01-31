package com.example.demo;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Lease implements Displayable{
    private static final AtomicInteger nextLeaseId = new AtomicInteger(1);
    private int leaseId;
    private Property property;
    private Tenant tenant;
    private LocalDateTime startDate;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public Lease() {
        this.property = null;
        this.tenant=null;
        this.startDate=null;
    }
    public Lease(Property property, Tenant tenant) {
        this.leaseId = getNextLeaseId();
        this.property = property;
        this.tenant = tenant;
        this.startDate=LocalDateTime.now();
    }
    public static synchronized int getNextLeaseId() {
        return nextLeaseId.getAndIncrement();
    }

    @Override
    public void displayInfo() {
        System.out.println("==================");
        System.out.println("Lease Information:");
        property.displayInfo();
        tenant.displayInfo();
        System.out.println("Starting date: "+startDate.format(dateFormatter));
        System.out.println("-------------");
        System.out.println("==================");
    }

    public int getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(int leaseId) {
        this.leaseId = leaseId;
    }


    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
