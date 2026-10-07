package com.aircraftmanagement.model;

import com.aircraftmanagement.exception.InvalidPilotException;

public class Pilot {
    private String pilotId;
    private String pilotName;
    private String licenseNumber;
    private int experience;
    private String availability;

    public Pilot(String pilotId, String pilotName, String licenseNumber, int experience)
            throws InvalidPilotException {
        if (pilotId == null || pilotId.trim().isEmpty()) throw new InvalidPilotException("Pilot ID cannot be empty.");
        if (pilotName == null || pilotName.trim().isEmpty()) throw new InvalidPilotException("Pilot name cannot be empty.");
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) throw new InvalidPilotException("License number cannot be empty.");
        if (experience < 0) throw new InvalidPilotException("Pilot experience cannot be negative.");
        this.pilotId = pilotId.trim();
        this.pilotName = pilotName.trim();
        this.licenseNumber = licenseNumber.trim();
        this.experience = experience;
        this.availability = "AVAILABLE";
    }

    public String getPilotId() { return pilotId; }
    public String getPilotName() { return pilotName; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getExperience() { return experience; }
    public String getAvailability() { return availability; }

    public void assignToFlight() { availability = "ASSIGNED"; }
    public void releaseFromFlight() { availability = "AVAILABLE"; }

    public void displayDetails() {
        System.out.println("\n--- Pilot ---");
        System.out.println("Pilot ID       : " + pilotId);
        System.out.println("Pilot Name     : " + pilotName);
        System.out.println("License Number : " + licenseNumber);
        System.out.println("Experience     : " + experience + " years");
        System.out.println("Availability   : " + availability);
    }
}
