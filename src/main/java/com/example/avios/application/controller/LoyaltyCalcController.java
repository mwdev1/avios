package com.example.avios.application.controller;

import com.example.avios.application.dto.RouteAviosPointsResponse;
import com.example.avios.domain.model.AviosFlightPoints;
import com.example.avios.domain.service.FlightRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class LoyaltyCalcController {

    @Autowired
    private FlightRouteService flightRouteService;

    @GetMapping(value = "/points/route/{routeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public RouteAviosPointsResponse calculateAvios(
            @PathVariable String routeId,
            @RequestParam(required = false) String cabinCode) {
        String[] routeCodes = routeId.split("-");
        validateRequestPathRouteId(routeCodes);
        List<AviosFlightPoints> flightPointsEarned;
        if (cabinCode == null) {
            flightPointsEarned = flightRouteService.calculateAviosForRoute(routeCodes[0], routeCodes[1]);
        } else {
            flightPointsEarned =  List.of(flightRouteService.calculateAviosForCabinCode(routeCodes[0], routeCodes[1], cabinCode));
        }
        log.info("API/v1/points/route/{routeId} request processed successfully: departureAirportCode:{}, arrivalAirportCode:{}",  routeCodes[0], routeCodes[1]);
        return new RouteAviosPointsResponse(routeCodes[0], routeCodes[1], flightPointsEarned);
    }

    private static void validateRequestPathRouteId(String[] routeCodes) {
        if (routeCodes.length != 2 || routeCodes[0].length() != 3 || routeCodes[1].length() != 3) {
            throw new IllegalArgumentException("Invalid request route id, must be in format XXX-XXX.");
        }
    }

}
