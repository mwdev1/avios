package com.example.avios.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FlightRoutes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_airport_code", length = 3, nullable = false)
    private String departureAirportCode;

    @Column(name = "arrival_airport_code", length = 3, nullable = false)
    private String arrivalAirportCode;

    @Column(name = "avios_earned", nullable = false)
    private Integer aviosEarned;

}