

INSERT INTO FlightRoutes (departure_airport_code, arrival_airport_code, avios_earned)
VALUES
('LHR', 'LAX', 4500),
('LHR', 'SFO', 4400),
('LHR', 'JFK', 3200),
('LGW', 'YYZ', 3250);

INSERT INTO CabinBonus (cabin_name, cabin_code, bonus_proportion)
VALUES
('World Traveller', 'M', 0.00),
('World Traveller Plus', 'W', 0.20),
('Club World', 'J', 0.50),
('First', 'F', 1.00);
