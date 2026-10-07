# Aircraft Management System

Core Java console-based Low-Level Design project implementing the supplied Aircraft Management System assignment.

## Requirements implemented
- Core Java only
- Console application using `Scanner`
- Arrays of objects for aircraft, pilots, and flights
- No database
- No Collection Framework
- Abstract `Aircraft` base class
- `PassengerAircraft`, `CargoAircraft`, `PrivateAircraft`
- `Maintenance` interface
- `Flight` -> `Aircraft` and `Pilot` Has-A relationships
- `AircraftManager`, `PilotManager`, `FlightManager`
- Custom checked exceptions
- `try-catch-finally`, `throw`, and `throws`
- Input validation including `NumberFormatException`
- Method overriding and runtime polymorphism
- Method overloading for aircraft search
- Flight scheduling and cancellation with state management

## Package structure
```text
com.aircraftmanagement
├── model
│   ├── Aircraft.java
│   ├── PassengerAircraft.java
│   ├── CargoAircraft.java
│   ├── PrivateAircraft.java
│   ├── Pilot.java
│   └── Flight.java
├── interface_
│   └── Maintenance.java
├── exception
│   ├── AircraftNotFoundException.java
│   ├── AircraftAlreadyExistsException.java
│   ├── AircraftNotAvailableException.java
│   ├── PilotNotFoundException.java
│   ├── PilotAlreadyExistsException.java
│   ├── PilotAlreadyAssignedException.java
│   ├── FlightNotFoundException.java
│   ├── FlightAlreadyExistsException.java
│   ├── InvalidFlightException.java
│   ├── InvalidAircraftException.java
│   └── InvalidPilotException.java
├── service
│   ├── AircraftManager.java
│   ├── PilotManager.java
│   └── FlightManager.java
└── main
    └── AircraftManagementApplication.java
```

## Compile and run
From the project root:

### Windows PowerShell / CMD
```text
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out com.aircraftmanagement.main.AircraftManagementApplication
```

### Linux/macOS
```bash
javac -d out $(find src -name "*.java")
java -cp out com.aircraftmanagement.main.AircraftManagementApplication
```

## OOP mapping
- Encapsulation: private fields in domain classes
- Inheritance: concrete aircraft classes extend `Aircraft`
- Abstraction: abstract `Aircraft` + `Maintenance` interface
- Polymorphism: `Aircraft` references store different concrete aircraft objects
- Overriding: `displayDetails()`, `getAircraftType()`, maintenance methods
- Overloading: `searchAircraft(String)` and `searchAircraft(String, String)`
- Has-A: `Flight` contains `Aircraft` and `Pilot` references
- Constructors: validate and initialize mandatory state
- Exceptions: business-rule failures use custom exceptions

## Important design decision
Flight scheduling validates the flight ID, route, aircraft, and pilot before changing aircraft/pilot state. Only after all validations succeed are the aircraft and pilot marked unavailable. This prevents partial state updates.
