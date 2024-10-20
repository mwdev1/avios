package com.example.avios.application.dto;

import com.example.avios.domain.model.AviosFlightPoints;

import java.util.List;

public record RouteAviosPointsResponse(String departureAirportCode, String arrivalAirportCode, List<AviosFlightPoints> flightPointsEarned) {
}
