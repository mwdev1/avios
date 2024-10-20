# Avios Flight Earning Calculator API

## Overview
This application provides a RESTful web service that calculates the number of Avios points customers can earn based on flight routes and cabin class. The customer will earn Avios based on the departure and arrival airports, and a bonus will be awarded depending on the cabin class. The API allows customers to input flight details and returns the Avios points they would earn for the specified flight.

If the departure and arrival airport combination is not listed in the predefined route table, a default minimum of 500 Avios points will be awarded. If the cabin class is not specified, the API will return the Avios points for all available cabin classes.

## Implementation notes

My apologies that it has taken that long, ended up to be a bit busy week. However, I followed the instructions to try not spend more than two hours on the task.
Though to be fair with other candidates, I probably have spent little more having time during the weekend, although total time including writing this message I think will be less than 4 hours. I liked and appreciated that there were clear time boundaries given for the task.

Although the task was fairly straightforward, I will not claim that it's production ready :) It is not and I think it's fair to say that having few ours is not enough for that. Though in production systems it shouldn't necessarily involve significant effort as there are often custom libraries and configurations handling these concerns based on carefully defined guidelines.

Cross-cutting concerns like error handling and logging, usually need to conform to wider standarisation across the system and environments and were deliberately neglected in this task.
They can take many forms. Therefore, I didn't attempt trying to invent some strategies specifically for this task, implementing just very basic features.
Validation to some extent would probably also fall in that category.

There are no environment profiles and very general app configuration. Real life case would certainly need some tuning and be more explicit.

I could include some frameworks like Zalando for error handling, Sleuth for tracing, logging conf etc which give some basic functionality out of the box but even that need some customisation so I left it out.

One more note regarding the airport codes interchangeability requirement. I am aware implementation might be making two database calls and could be said to be not efficient, but I fauvored simple working solution avoiding any premature optimisation attempts.

Will be more than happy to discuss any implementation details or things missing in the project for production systems in case of questions!


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

