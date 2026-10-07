package com.aircraftmanagement.model;

import com.aircraftmanagement.exception.InvalidAircraftException;

public class PassengerAircraft extends Aircraft {
    private int numberOfPassengers;
    private boolean businessClassAvailable;
    private boolean maintenanceRequired;

    public PassengerAircraft(String aircraftId, String model, String manufacturer,
                             int capacity, int numberOfPassengers, boolean businessClassAvailable)
            throws InvalidAircraftException {
        super(aircraftId, model, manufacturer, capacity);
        if (numberOfPassengers <= 0 || numberOfPassengers > capacity) {
            throw new InvalidAircraftException("Number of passengers must be between 1 and capacity.");
        }
        this.numberOfPassengers = numberOfPassengers;
        this.businessClassAvailable = businessClassAvailable;
        this.maintenanceRequired = false;
    }

    @Override
    public void displayDetails() {
        System.out.println("\n--- Passenger Aircraft ---");
        printCommonDetails();
        System.out.println("Passenger Seats: " + numberOfPassengers);
        System.out.println("Business Class : " + (businessClassAvailable ? "YES" : "NO"));
        System.out.println("Maintenance    : " + (maintenanceRequired ? "REQUIRED" : "NOT REQUIRED"));
    }

    @Override
    public String getAircraftType() { return "PASSENGER"; }

    @Override
    public void performMaintenance() {
        maintenanceRequired = false;
        System.out.println("Maintenance completed for passenger aircraft " + getAircraftId());
    }

    @Override
    public boolean isMaintenanceRequired() { return maintenanceRequired; }
}
