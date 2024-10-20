package com.example.avios.domain.repo;

import com.example.avios.domain.model.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long> {

    Optional<FlightRoute> findByDepartureAirportCodeAndArrivalAirportCode(String arrival, String departure);

}