package com.aircraftmanagement.service;

import com.aircraftmanagement.exception.*;
import com.aircraftmanagement.model.Aircraft;
import com.aircraftmanagement.model.Flight;
import com.aircraftmanagement.model.Pilot;

public class FlightManager {
    private final Flight[] flights;
    private int flightCount;
    private final AircraftManager aircraftManager;
    private final PilotManager pilotManager;

    public FlightManager(int capacity, AircraftManager aircraftManager, PilotManager pilotManager) {
        if (capacity <= 0) throw new IllegalArgumentException("Array capacity must be greater than zero.");
        this.flights = new Flight[capacity];
        this.aircraftManager = aircraftManager;
        this.pilotManager = pilotManager;
    }

    public void scheduleFlight(String flightId, String source, String destination,
                               String aircraftId, String pilotId)
            throws FlightAlreadyExistsException, AircraftNotFoundException,
            PilotNotFoundException, AircraftNotAvailableException,
            PilotAlreadyAssignedException, InvalidFlightException {
        if (flightCount == flights.length) {
            throw new InvalidFlightException("Flight storage is full.");
        }
        if (flightId == null || flightId.trim().isEmpty()) {
            throw new InvalidFlightException("Flight ID cannot be empty.");
        }
        if (findFlightInternal(flightId) != null) {
            throw new FlightAlreadyExistsException("Flight " + flightId + " is already registered.");
        }
        if (source == null || destination == null || source.trim().isEmpty() || destination.trim().isEmpty()) {
            throw new InvalidFlightException("Source and destination cannot be empty.");
        }
        if (source.trim().equalsIgnoreCase(destination.trim())) {
            throw new InvalidFlightException("Source and destination cannot be the same.");
        }

        // Validate every dependency before changing any object state.
        Aircraft aircraft = aircraftManager.searchAircraft(aircraftId);
        if (!"AVAILABLE".equals(aircraft.getStatus())) {
            throw new AircraftNotAvailableException(
                    "Aircraft " + aircraftId + " is currently assigned to another flight.");
        }

        Pilot pilot = pilotManager.searchPilot(pilotId);
        if (!"AVAILABLE".equals(pilot.getAvailability())) {
            throw new PilotAlreadyAssignedException(
                    "Pilot " + pilotId + " is already assigned to an active flight.");
        }

        // State changes happen only after all validations succeed.
        try {
            Flight flight = new Flight(flightId, source, destination, aircraft, pilot);
            aircraft.markUnavailable();
            pilot.assignToFlight();
            flights[flightCount++] = flight;
        } catch (InvalidAircraftException e) {
            throw new InvalidFlightException(e.getMessage());
        }
    }

    public Flight searchFlight(String flightId) throws FlightNotFoundException {
        Flight flight = findFlightInternal(flightId);
        if (flight == null) throw new FlightNotFoundException("Flight with ID " + flightId + " does not exist.");
        return flight;
    }

    public void displayAllFlights() {
        if (flightCount == 0) {
            System.out.println("No flights registered.");
            return;
        }
        for (int i = 0; i < flightCount; i++) flights[i].displayDetails();
    }

    public void cancelFlight(String flightId) throws FlightNotFoundException, InvalidFlightException {
        Flight flight = searchFlight(flightId);
        flight.cancel();
    }

    private Flight findFlightInternal(String flightId) {
        if (flightId == null) return null;
        for (int i = 0; i < flightCount; i++) {
            if (flights[i].getFlightId().equalsIgnoreCase(flightId.trim())) return flights[i];
        }
        return null;
    }
}
