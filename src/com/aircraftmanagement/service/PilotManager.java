package com.aircraftmanagement.service;

import com.aircraftmanagement.exception.*;
import com.aircraftmanagement.model.Pilot;

public class PilotManager {
    private final Pilot[] pilots;
    private int pilotCount;

    public PilotManager(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Array capacity must be greater than zero.");
        pilots = new Pilot[capacity];
    }

    public void addPilot(Pilot pilot) throws PilotAlreadyExistsException, InvalidPilotException {
        if (pilot == null) throw new InvalidPilotException("Pilot cannot be null.");
        if (pilotCount == pilots.length) throw new InvalidPilotException("Pilot storage is full.");
        if (exists(pilot.getPilotId())) {
            throw new PilotAlreadyExistsException("Pilot " + pilot.getPilotId() + " is already registered.");
        }
        pilots[pilotCount++] = pilot;
    }

    public Pilot searchPilot(String pilotId) throws PilotNotFoundException {
        if (pilotId == null || pilotId.trim().isEmpty()) {
            throw new PilotNotFoundException("Pilot ID cannot be empty.");
        }
        for (int i = 0; i < pilotCount; i++) {
            if (pilots[i].getPilotId().equalsIgnoreCase(pilotId.trim())) return pilots[i];
        }
        throw new PilotNotFoundException("Pilot with ID " + pilotId + " does not exist.");
    }

    public void displayAllPilots() {
        if (pilotCount == 0) {
            System.out.println("No pilots registered.");
            return;
        }
        for (int i = 0; i < pilotCount; i++) pilots[i].displayDetails();
    }

    private boolean exists(String pilotId) {
        for (int i = 0; i < pilotCount; i++) {
            if (pilots[i].getPilotId().equalsIgnoreCase(pilotId.trim())) return true;
        }
        return false;
    }
}
