package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

public class PropertyManager {
    private List<Property> properties;

    public PropertyManager() {
        this.properties = new ArrayList<>();
    }

    public PropertyManager(List<Property> properties) {
        this.properties = properties;
    }

    public synchronized void addProperty(Property property) {
        if (isDuplicateProperty(property.getAddress())) {
            throw new DuplicatePropertyException("Property with address " + property.getAddress() + " already exists.");
        }
        properties.add(property);
    }

    public List<Property> getProperties() {
        return properties;
    }
    public List<Property> getAvailableProperties() {
        List<Property> properties1=new ArrayList<Property>();
        for(Property p:properties){
            if(p.getRentable()==true)
                properties1.add(p);
        }
        return properties1;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void sortProperties() {
        Collections.sort(this.properties);
    }

    public synchronized void viewProperties() {
        System.out.println("===ALL PROPERTIES===");
        for (Property property : this.properties) {
            property.displayInfo();
        }
    }

    public Map<Integer, List<Property>> propertiesByBedrooms() {
        return this.properties.stream()
                .filter(Property::getRentable)
                .collect(Collectors.groupingBy(Property::getNumBedrooms));
    }

    public Map<String, List<Property>> propertiesByType() {
        return this.properties.stream()
                .filter(Property::getRentable)
                .collect(Collectors.groupingBy(Property::getType));
    }

    public Map<Integer, List<Property>> propertiesByBedrooms1() {
        return this.properties.stream()
                .collect(Collectors.groupingBy(Property::getNumBedrooms));
    }

    public Map<String, List<Property>> propertiesByType1() {
        return this.properties.stream()
                .collect(Collectors.groupingBy(Property::getType));
    }


    public Set<String> typesOfProperties() {
        return this.properties.stream().map(Property::getType).collect(Collectors.toSet());
    }

    public Set<Integer> numberOfBedrooms() {
        return this.properties.stream().map(Property::getNumBedrooms).collect(Collectors.toSet());
    }

    public synchronized Property searchProperty(int id) {
        for (Property property : this.properties) {
            if (property.getPropertyId() == id) {
                if (property.isRentable()) {
                    //System.out.println("THE PROPERTY IS AVAILABLE.");
                    return property;
                } else {
                    //System.out.println("SORRY, THIS PROPERTY IS NOT AVAILABLE");
                    return null;
                }
            }
        }
       // System.out.println("SORRY! THE PROPERTY WITH THIS ID DOESN'T EXIST.");
        return null;
    }

    public boolean isDuplicateProperty(String address) {
        for (Property property : properties) {
            if (property.getAddress().equals(address)) {
                return true;
            }
        }
        return false;
    }


}

