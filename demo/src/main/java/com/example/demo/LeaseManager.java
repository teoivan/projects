package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaseManager {
    List<Lease> leases;

    public LeaseManager(List<Lease> leases) {
        this.leases = leases;
    }

    public LeaseManager() {
        this.leases = new ArrayList<>();
    }

    public List<Lease> getLeases() {
        return leases;
    }

    public void setLeases(List<Lease> leases) {
        this.leases = leases;
    }

    public synchronized void addLease(Lease lease) {
        leases.add(lease);
    }
    public synchronized void displayInfo() {
        System.out.println(" ===ALL LEASES===");
        for (Lease lease : leases) {
            System.out.println("Lease ID:"+lease.getLeaseId());
            lease.displayInfo();
        }
    }
}
