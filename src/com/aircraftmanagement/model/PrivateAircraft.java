package com.aircraftmanagement.model;

import com.aircraftmanagement.exception.InvalidAircraftException;

public class PrivateAircraft extends Aircraft {
    private String ownerName;
    private String luxuryLevel;
    private boolean maintenanceRequired;

    public PrivateAircraft(String aircraftId, String model, String manufacturer,
                           int capacity, String ownerName, String luxuryLevel)
            throws InvalidAircraftException {
        super(aircraftId, model, manufacturer, capacity);
        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new InvalidAircraftException("Owner name cannot be empty.");
        }
        if (luxuryLevel == null || luxuryLevel.trim().isEmpty()) {
            throw new InvalidAircraftException("Luxury level cannot be empty.");
        }
        this.ownerName = ownerName.trim();
        this.luxuryLevel = luxuryLevel.trim();
        this.maintenanceRequired = false;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Private Aircraft ---");
        printCommonDetails();
        System.out.println("Owner Name     : " + ownerName);
        System.out.println("Luxury Level   : " + luxuryLevel);
        System.out.println("Maintenance    : " + (maintenanceRequired ? "REQUIRED" : "NOT REQUIRED"));
    }

    @Override
    public String getAircraftType() { return "PRIVATE"; }

    @Override
    public void performMaintenance() {
        maintenanceRequired = false;
        System.out.println("Maintenance completed for private aircraft " + getAircraftId());
    }

    @Override
    public boolean isMaintenanceRequired() { return maintenanceRequired; }
}
