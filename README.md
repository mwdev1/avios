# Avios Flight Earning Calculator API

## Overview
This application provides a RESTful web service that calculates the number of Avios points customers can earn based on flight routes and cabin class. The customer will earn Avios based on the departure and arrival airports, and a bonus will be awarded depending on the cabin class. The API allows customers to input flight details and returns the Avios points they would earn for the specified flight.

If the departure and arrival airport combination is not listed in the predefined route table, a default minimum of 500 Avios points will be awarded. If the cabin class is not specified, the API will return the Avios points for all available cabin classes.

## Avios Calculation
The Avios earned are determined by two main factors:
1. **Flight Route**: Based on the departure and arrival airport codes.
2. **Cabin Bonus**: Additional points awarded depending on the cabin class:
    - World Traveller (`M`): 0% Bonus
    - World Traveller Plus (`W`): 20% Bonus
    - Club World (`J`): 50% Bonus
    - First (`F`): 100% Bonus

### Predefined Routes and Avios
| Departure Airport Code | Arrival Airport Code | Avios Earned |
|------------------------|----------------------|--------------|
| LHR                    | LAX                  | 4500         |
| LHR                    | SFO                  | 4400         |
| LHR                    | JFK                  | 3200         |
| LGW                    | YYZ                  | 3250         |

For any other airport route, the minimum Avios earned will be 500.

## API Specification

### Endpoint
The API exposes a single endpoint for Avios calculation:

### Request URI path Parameter
- `routeId` (required): Two airport codes in format XXX-XXX (e.g., LHR-LAX).

### Request Parameters
- `cabinCode` (optional): The code of the cabin class (`M`, `W`, `J`, or `F`).

### Example Requests

1. **Calculate Avios for a specific cabin class**:
    ```http
    GET /api/v1/points/route/LHR-JFK?cabinCode=J
    ```
   **Response**:
    ```json
    {
      "departureAirportCode": "LHR",
      "arrivalAirportCode": "JFK",
      "flightPointsEarned": [
        {
          "cabinCode": "J",
          "pointsEarned": 4800
        }
      ]
    }
    ```

2. **Calculate Avios for all cabin classes**:
    ```http
    GET /api/v1/points/route/LHR-JFK
    ```
   **Response**:
    ```json
    {
      "departureAirportCode": "LHR",
      "arrivalAirportCode": "JFK",
      "flightPointsEarned": [
        {
          "cabinCode": "M",
          "pointsEarned": 3200
        },
        {
          "cabinCode": "W",
          "pointsEarned": 3840
        },
        {
          "cabinCode": "J",
          "pointsEarned": 4800
        },
        {
          "cabinCode": "F",
          "pointsEarned": 6400
        }
      ]
    }
    ```


The application will start on `http://localhost:8080`.

