package com.example.demo;

import java.util.*;

public class TenantManager {
    List<Tenant> tenants;

    public TenantManager(List<Tenant> tenants) {
        this.tenants = tenants;
    }
    public TenantManager()
    {
        this.tenants=new ArrayList<>();
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public void sortTenants(){
        Collections.sort(tenants);
    }

    public synchronized void addTenant(Tenant tenant) {
        if (isDuplicateTenant(tenant.getName())) {
            throw new DuplicateTenantException("Tenant with the same name already exists: " + tenant.getName());
        }
        tenants.add(tenant);
    }
    public boolean isDuplicateTenant(String tenantName) {
        for (Tenant tenant : tenants) {
            if (tenant.getName().equals(tenantName)) {
                return true;
            }
        }
        return false;
    }
}
