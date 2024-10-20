package com.example.avios.domain.service;

import com.example.avios.domain.model.FlightRoute;
import com.example.avios.domain.model.AviosFlightPoints;
import com.example.avios.domain.repo.FlightRouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightRouteService {

    public static final int DEFAULT_AVIOS_BASE_AMOUNT = 500;

    public FlightRouteService(FlightRouteRepository flightRouteRepository, CabinBonusService cabinBonusService) {
        this.flightRouteRepository = flightRouteRepository;
        this.cabinBonusService = cabinBonusService;
    }

    private FlightRouteRepository flightRouteRepository;
    private CabinBonusService cabinBonusService;

    public AviosFlightPoints calculateAviosForCabinCode(String departure, String arrival, String cabinCode) {
        Integer basePoints = fetchPointsForRouteCodes(departure, arrival);
        return cabinBonusService.calculateBonusForCabin(cabinCode, basePoints);
    }

    public List<AviosFlightPoints> calculateAviosForRoute(String departure, String arrival) {
        Integer basePoints = fetchPointsForRouteCodes(departure, arrival);
        return cabinBonusService.calculateBonusForAllCabins(basePoints);
    }

    private Integer fetchPointsForRouteCodes(String departure, String arrival) {
        Optional<FlightRoute> route = flightRouteRepository.findByDepartureAirportCodeAndArrivalAirportCode(departure.toUpperCase(), arrival.toUpperCase());
        if (route.isEmpty()) {
            route = flightRouteRepository.findByDepartureAirportCodeAndArrivalAirportCode(arrival.toUpperCase(), departure.toUpperCase());
        }
       return route.map(FlightRoute::getAviosEarned)
        .orElse(DEFAULT_AVIOS_BASE_AMOUNT);
    }

}
