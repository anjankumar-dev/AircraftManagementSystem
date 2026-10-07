package com.aircraftmanagement.service;

import com.aircraftmanagement.exception.*;
import com.aircraftmanagement.model.Aircraft;

public class AircraftManager {
    private final Aircraft[] aircrafts;
    private int aircraftCount;

    public AircraftManager(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Array capacity must be greater than zero.");
        aircrafts = new Aircraft[capacity];
    }

    public void addAircraft(Aircraft aircraft)
            throws AircraftAlreadyExistsException, InvalidAircraftException {
        if (aircraft == null) throw new InvalidAircraftException("Aircraft cannot be null.");
        if (aircraftCount == aircrafts.length) {
            throw new InvalidAircraftException("Aircraft storage is full. No more aircraft can be registered.");
        }
        if (exists(aircraft.getAircraftId())) {
            throw new AircraftAlreadyExistsException(
                    "Aircraft " + aircraft.getAircraftId() + " is already registered.");
        }
        aircrafts[aircraftCount++] = aircraft;
    }

    public Aircraft searchAircraft(String aircraftId) throws AircraftNotFoundException {
        if(aircrafts[0] == null)
            throw new RuntimeException("There is no Aircrafts are registered.");
        if (aircraftId == null || aircraftId.trim().isEmpty()) {
            throw new AircraftNotFoundException("Aircraft ID cannot be empty.");
        }
        for (int i = 0; i < aircraftCount; i++) {
            if (aircrafts[i].getAircraftId().equalsIgnoreCase(aircraftId.trim())) return aircrafts[i];
        }
        throw new AircraftNotFoundException("Aircraft with ID " + aircraftId + " does not exist.");
    }

    // Method overloading: related search using two parameters.
    public Aircraft searchAircraft(String model, String manufacturer)
            throws AircraftNotFoundException {
        for (int i = 0; i < aircraftCount; i++) {
            if (aircrafts[i].getModel().equalsIgnoreCase(model.trim())
                    && aircrafts[i].getManufacturer().equalsIgnoreCase(manufacturer.trim())) {
                return aircrafts[i];
            }
        }
        throw new AircraftNotFoundException(
                "No aircraft found for model " + model + " and manufacturer " + manufacturer + ".");
    }

    public void displayAllAircraft() {
        if (aircraftCount == 0) {
            System.out.println("No aircraft registered.");
            return;
        }
        for (int i = 0; i < aircraftCount; i++) aircrafts[i].displayDetails();
    }

    private boolean exists(String aircraftId) {
        for (int i = 0; i < aircraftCount; i++) {
            if (aircrafts[i].getAircraftId().equalsIgnoreCase(aircraftId.trim())) return true;
        }
        return false;
    }

    public int getAircraftCount() {
        return aircraftCount;
    }
}
