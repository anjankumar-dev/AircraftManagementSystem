package com.aircraftmanagement.model;

import com.aircraftmanagement.interface_.Maintenance;
import com.aircraftmanagement.exception.InvalidAircraftException;

public abstract class Aircraft implements Maintenance {
    private String aircraftId;
    private String model;
    private String manufacturer;
    private int capacity;
    private String status;

    protected Aircraft(String aircraftId, String model, String manufacturer, int capacity)
            throws InvalidAircraftException {
        validateText(aircraftId, "Aircraft ID");
        validateText(model, "Model");
        validateText(manufacturer, "Manufacturer");
        validateCapacity(capacity);
        this.aircraftId = aircraftId.trim();
        this.model = model.trim();
        this.manufacturer = manufacturer.trim();
        this.capacity = capacity;
        this.status = "AVAILABLE";
    }

    private void validateText(String value, String field) throws InvalidAircraftException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidAircraftException(field + " cannot be empty.");
        }
    }

    protected void validateCapacity(int capacity) throws InvalidAircraftException {
        if (capacity <= 0) {
            throw new InvalidAircraftException("Capacity must be greater than zero.");
        }
    }

    public abstract void displayDetails();

    public void start() throws InvalidAircraftException {
        if ("RETIRED".equals(status)) {
            throw new InvalidAircraftException("Retired aircraft cannot be started.");
        }
        status = "AVAILABLE";
    }

    public void stop() throws InvalidAircraftException {
        if ("IN_FLIGHT".equals(status)) {
            throw new InvalidAircraftException("Aircraft assigned to a flight cannot be stopped.");
        }
        status = "AVAILABLE";
    }

    public abstract String getAircraftType();

    public String getAircraftId() { return aircraftId; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public int getCapacity() { return capacity; }
    public String getStatus() { return status; }

    public void markUnavailable() throws InvalidAircraftException {
        if (!"AVAILABLE".equals(status)) {
            throw new InvalidAircraftException("Aircraft " + aircraftId + " is not available.");
        }
        status = "IN_FLIGHT";
    }

    public void markAvailable() {
        if (!"RETIRED".equals(status)) {
            status = "AVAILABLE";
        }
    }

    public void retire() throws InvalidAircraftException {
        if ("IN_FLIGHT".equals(status)) {
            throw new InvalidAircraftException("Aircraft assigned to a flight cannot be retired.");
        }
        status = "RETIRED";
    }

    protected void printCommonDetails() {
        System.out.println("Aircraft ID   : " + aircraftId+"\n"+"Type          : " + getAircraftType());
//        System.out.println();
        System.out.println("Model         : " + model);
        System.out.println("Manufacturer  : " + manufacturer);
        System.out.println("Capacity      : " + capacity);
        System.out.println("Status        : " + status);
    }
}
