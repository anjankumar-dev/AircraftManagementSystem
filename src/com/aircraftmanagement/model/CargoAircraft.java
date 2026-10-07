package com.aircraftmanagement.model;

import com.aircraftmanagement.exception.InvalidAircraftException;

public class CargoAircraft extends Aircraft {
    private double cargoCapacity;
    private String cargoType;
    private boolean maintenanceRequired;

    public CargoAircraft(String aircraftId, String model, String manufacturer,
                         int capacity, double cargoCapacity, String cargoType)
            throws InvalidAircraftException {
        super(aircraftId, model, manufacturer, capacity);
        if (cargoCapacity <= 0) {
            throw new InvalidAircraftException("Cargo capacity must be greater than zero.");
        }
        if (cargoType == null || cargoType.trim().isEmpty()) {
            throw new InvalidAircraftException("Cargo type cannot be empty.");
        }
        this.cargoCapacity = cargoCapacity;
        this.cargoType = cargoType.trim();
        this.maintenanceRequired = false;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Cargo Aircraft ---");
        printCommonDetails();
        System.out.println("Cargo Capacity : " + cargoCapacity + " tons");
        System.out.println("Cargo Type     : " + cargoType);
        System.out.println("Maintenance    : " + (maintenanceRequired ? "REQUIRED" : "NOT REQUIRED"));
    }

    @Override
    public String getAircraftType() { return "CARGO"; }

    @Override
    public void performMaintenance() {
        maintenanceRequired = false;
        System.out.println("Maintenance completed for cargo aircraft " + getAircraftId());
    }

    @Override
    public boolean isMaintenanceRequired() { return maintenanceRequired; }
}
