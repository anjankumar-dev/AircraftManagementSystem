# LLD – Aircraft Management System

```text
                         <<interface>>
                         Maintenance
                         + performMaintenance()
                         + isMaintenanceRequired()
                                ^
                                |
                    +-------------------------+
                    |   Aircraft <<abstract>> |
                    +-------------------------+
                    | - aircraftId            |
                    | - model                 |
                    | - manufacturer          |
                    | - capacity              |
                    | - status                |
                    +-------------------------+
                    | + displayDetails()      |
                    | + start()               |
                    | + stop()                |
                    | + getAircraftType()     |
                    | + markUnavailable()     |
                    | + markAvailable()       |
                    +-------------------------+
                       ^          ^          ^
                       |          |          |
          +------------+    +----+-----+    +-------------+
          |                 |            |                  |
+-------------------+ +----------------+ +------------------+
| PassengerAircraft | | CargoAircraft  | | PrivateAircraft  |
+-------------------+ +----------------+ +------------------+

+----------------+
|     Pilot      |
+----------------+
| - pilotId      |
| - pilotName    |
| - licenseNo    |
| - experience   |
| - availability |
+----------------+

              has-a                 has-a
+-------------------------+----------------------+
|                                                |
v                                                v
+----------------+
|     Flight     |
+----------------+
| - flightId     |
| - source       |
| - destination  |
| - aircraft     | -----> Aircraft
| - pilot        | -----> Pilot
| - flightStatus |
+----------------+

+------------------+      owns array       +----------------+
| AircraftManager  | --------------------> | Aircraft[]     |
+------------------+                       +----------------+

+------------------+      owns array       +----------------+
| PilotManager     | --------------------> | Pilot[]        |
+------------------+                       +----------------+

+------------------+      owns array       +----------------+
| FlightManager    | --------------------> | Flight[]       |
+------------------+                       +----------------+
```

## Responsibilities
- Model classes: state and object behavior.
- Managers: array storage, search, duplicate checks, and business operations.
- Exceptions: meaningful business failures.
- Main application: console input, menu coordination, and exception handling.
