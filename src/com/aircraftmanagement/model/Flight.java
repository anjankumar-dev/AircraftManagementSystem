package com.aircraftmanagement.model;

import com.aircraftmanagement.exception.InvalidFlightException;

public class Flight {
    private String flightId;
    private String source;
    private String destination;
    private Aircraft aircraft;
    private Pilot pilot;
    private String flightStatus;

    public Flight(String flightId, String source, String destination,
                  Aircraft aircraft, Pilot pilot) throws InvalidFlightException {
        if (flightId == null || flightId.trim().isEmpty()) throw new InvalidFlightException("Flight ID cannot be empty.");
        if (source == null || source.trim().isEmpty()) throw new InvalidFlightException("Source cannot be empty.");
        if (destination == null || destination.trim().isEmpty()) throw new InvalidFlightException("Destination cannot be empty.");
        if (source.trim().equalsIgnoreCase(destination.trim())) {
            throw new InvalidFlightException("Source and destination cannot be the same.");
        }
        if (aircraft == null) throw new InvalidFlightException("Aircraft is required.");
        if (pilot == null) throw new InvalidFlightException("Pilot is required.");
        this.flightId = flightId.trim();
        this.source = source.trim();
        this.destination = destination.trim();
        this.aircraft = aircraft;
        this.pilot = pilot;
        this.flightStatus = "SCHEDULED";
    }

    public String getFlightId() { return flightId; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public Aircraft getAircraft() { return aircraft; }
    public Pilot getPilot() { return pilot; }
    public String getFlightStatus() { return flightStatus; }

    public void cancel() throws InvalidFlightException {
        if ("CANCELLED".equals(flightStatus)) {
            throw new InvalidFlightException("Flight " + flightId + " is already cancelled.");
        }
        flightStatus = "CANCELLED";
        aircraft.markAvailable();
        pilot.releaseFromFlight();
    }

    public void displayDetails() {
        System.out.println("\n--- Flight ---");
        System.out.println("Flight ID     : " + flightId);
        System.out.println("Route         : " + source + " -> " + destination);
        System.out.println("Aircraft ID   : " + aircraft.getAircraftId());
        System.out.println("Aircraft Type : " + aircraft.getAircraftType());
        System.out.println("Pilot ID      : " + pilot.getPilotId());
        System.out.println("Pilot Name    : " + pilot.getPilotName());
        System.out.println("Status        : " + flightStatus);
    }
}
