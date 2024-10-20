

CREATE TABLE FlightRoutes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    departure_airport_code VARCHAR(3) NOT NULL,
    arrival_airport_code VARCHAR(3) NOT NULL,
    avios_earned INT NOT NULL
);

CREATE UNIQUE INDEX unique_flight_route
ON FlightRoutes (departure_airport_code, arrival_airport_code);

CREATE TABLE CabinBonus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cabin_name VARCHAR(50) NOT NULL,
    cabin_code CHAR(1) NOT NULL,
    bonus_proportion DECIMAL(4, 3) NOT NULL,
    CONSTRAINT unique_cabin_code UNIQUE (cabin_code)
);