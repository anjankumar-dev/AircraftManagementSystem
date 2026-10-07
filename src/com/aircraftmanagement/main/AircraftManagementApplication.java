package com.aircraftmanagement.main;

import com.aircraftmanagement.exception.*;
import com.aircraftmanagement.model.*;
import com.aircraftmanagement.service.AircraftManager;
import com.aircraftmanagement.service.FlightManager;
import com.aircraftmanagement.service.PilotManager;

import java.util.Scanner;

public class AircraftManagementApplication {
    private static final int MAX_AIRCRAFT = 50;
    private static final int MAX_PILOTS = 50;
    private static final int MAX_FLIGHTS = 100;

    private final Scanner scanner = new Scanner(System.in);
    private final AircraftManager aircraftManager = new AircraftManager(MAX_AIRCRAFT);
    private final PilotManager pilotManager = new PilotManager(MAX_PILOTS);
    private final FlightManager flightManager = new FlightManager(MAX_FLIGHTS, aircraftManager, pilotManager);

    public static void main(String[] args) {
        new AircraftManagementApplication().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = readInt("Enter your choice: ");
                switch (choice) {
                    case 1 :
                        registerAircraft();
                        break;
                    case 2 :
                        aircraftManager.displayAllAircraft();
                        break;
                    case 3 :
                        searchAircraft();
                        break;
                    case 4 :
                        addPilot();
                        break;
                    case 5 :
                        pilotManager.displayAllPilots();
                        break;
                    case 6 :
                        searchPilot();
                        break;
                    case 7 :
                        scheduleFlight();
                        break;
                    case 8 :
                        flightManager.displayAllFlights();
                        break;
                    case 9 :
                        searchFlight();
                        break;
                    case 10 :
                        cancelFlight();
                        break;
                    case 11 : {
                        running = false;
                        System.out.println("Exiting Aircraft Management System. Goodbye!");
                        break;
                    }
                    default : System.out.println("Invalid menu option. Please choose 1-11.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (AircraftAlreadyExistsException | AircraftNotFoundException |
                     AircraftNotAvailableException | PilotNotFoundException |
                     PilotAlreadyAssignedException | FlightNotFoundException |
                     FlightAlreadyExistsException | InvalidFlightException |
                     InvalidAircraftException | PilotAlreadyExistsException |
                     InvalidPilotException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } finally {
                if (running) System.out.println("Returning to main menu...\n");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("=========================================");
        System.out.println("       AIRCRAFT MANAGEMENT SYSTEM");
        System.out.println("=========================================");
        System.out.println("1. Register Aircraft");
        System.out.println("2. Display All Aircraft");
        System.out.println("3. Search Aircraft");
        System.out.println("4. Add Pilot");
        System.out.println("5. Display All Pilots");
        System.out.println("6. Search Pilot");
        System.out.println("7. Schedule Flight");
        System.out.println("8. Display All Flights");
        System.out.println("9. Search Flight");
        System.out.println("10. Cancel Flight");
        System.out.println("11. Exit");
        System.out.println("=========================================");
    }

    private void registerAircraft()
            throws AircraftAlreadyExistsException, InvalidAircraftException {
        System.out.println("\nSelect Aircraft Type:");
        System.out.println("1. Passenger");
        System.out.println("2. Cargo");
        System.out.println("3. Private");
        int type = readInt("Enter choice: ");

        String id = readText("Enter Aircraft ID: ");
        String model = readText("Enter Model: ");
        String manufacturer = readText("Enter Manufacturer: ");
        int capacity = readInt("Enter Capacity: ");

        Aircraft aircraft;
        switch (type) {
            case 1 : {
                int passengers = readInt("Enter Number of Passenger Seats: ");
                boolean businessClass = readYesNo("Business Class Available? (Y/N): ");
                aircraft = new PassengerAircraft(id, model, manufacturer, capacity, passengers, businessClass);
                break;
            }
            case 2 : {
                double cargoCapacity = readDouble("Enter Cargo Capacity (tons): ");
                String cargoType = readText("Enter Cargo Type: ");
                aircraft = new CargoAircraft(id, model, manufacturer, capacity, cargoCapacity, cargoType);
                break;
            }
            case 3 : {
                String owner = readText("Enter Owner Name: ");
                String luxury = readText("Enter Luxury Level: ");
                aircraft = new PrivateAircraft(id, model, manufacturer, capacity, owner, luxury);
                break;
            }
            default : {
                System.out.println("Invalid aircraft type.");
                return;
            }
        }

        aircraftManager.addAircraft(aircraft);
        System.out.println("Aircraft " + id + " registered successfully.");
    }

    private void searchAircraft() throws AircraftNotFoundException {
        if(aircraftManager.getAircraftCount() ==0 )
            throw new AircraftNotFoundException("There is no Aircrafts are registered");
        System.out.println("\n1. Search by Aircraft ID");
        System.out.println("2. Search by Model and Manufacturer");
        int choice = readInt("Enter choice: ");
        Aircraft aircraft;
        if (choice == 1) {
            aircraft = aircraftManager.searchAircraft(readText("Enter Aircraft ID: "));
        } else if (choice == 2) {
            aircraft = aircraftManager.searchAircraft(
                    readText("Enter Model: "), readText("Enter Manufacturer: "));
        } else {
            System.out.println("Invalid search option.");
            return;
        }
        aircraft.displayDetails();
    }

    private void addPilot() throws PilotAlreadyExistsException, InvalidPilotException {
        String id = readText("Pilot ID: ");
        String name = readText("Pilot Name: ");
        String license = readText("License Number: ");
        int experience = readInt("Experience (years): ");

        Pilot pilot = new Pilot(id, name, license, experience);
        pilotManager.addPilot(pilot);
        System.out.println("Pilot " + id + " added successfully.");
    }

    private void searchPilot() throws PilotNotFoundException {
        Pilot pilot = pilotManager.searchPilot(readText("Enter Pilot ID: "));
        pilot.displayDetails();
    }

    private void scheduleFlight()
            throws FlightAlreadyExistsException, AircraftNotFoundException,
            PilotNotFoundException, AircraftNotAvailableException,
            PilotAlreadyAssignedException, InvalidFlightException {
        String flightId = readText("Enter Flight ID: ");
        String source = readText("Enter Source: ");
        String destination = readText("Enter Destination: ");
        String aircraftId = readText("Enter Aircraft ID: ");
        String pilotId = readText("Enter Pilot ID: ");

        flightManager.scheduleFlight(flightId, source, destination, aircraftId, pilotId);
        System.out.println("Flight " + flightId + " scheduled successfully.");
    }

    private void searchFlight() throws FlightNotFoundException {
        Flight flight = flightManager.searchFlight(readText("Enter Flight ID: "));
        flight.displayDetails();
    }

    private void cancelFlight() throws FlightNotFoundException, InvalidFlightException {
        String flightId = readText("Enter Flight ID: ");
        flightManager.cancelFlight(flightId);
        System.out.println("Flight " + flightId + " cancelled successfully.");
    }

    private String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        String input = readText(prompt);
        return Integer.parseInt(input);
    }

    private double readDouble(String prompt) {
        String input = readText(prompt);
        return Double.parseDouble(input);
    }

    private boolean readYesNo(String prompt) throws InvalidAircraftException {
        String input = readText(prompt);
        if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) return true;
        if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) return false;
        throw new InvalidAircraftException("Please enter Y/YES or N/NO.");
    }
}
