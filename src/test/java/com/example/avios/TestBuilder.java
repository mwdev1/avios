package com.example.avios;

import com.example.avios.domain.model.CabinBonus;
import com.example.avios.domain.model.FlightRoute;

import java.math.BigDecimal;

public class TestBuilder {

    public static final String AIRPORT_CODE_LHR = "LHR";
    public static final String AIRPORT_CODE_LAX = "LAX";
    public static final String CABIN_CODE_W = "W";
    public static final String CABIN_CODE_J = "J";
    public static final BigDecimal BONUS_W_PERCENTAGE_20 = BigDecimal.valueOf(0.20);
    public static final BigDecimal BONUS_J_PERCENTAGE_50 = BigDecimal.valueOf(0.50);

    public static final FlightRoute FLIGHT_ROUTE_LAX_LHR = new FlightRoute(
            null,
            AIRPORT_CODE_LAX,
            AIRPORT_CODE_LHR,
            1000
    );

    public static final CabinBonus CABIN_BONUS_W = new CabinBonus(
        null,
        "World Traveller Plus",
        CABIN_CODE_W,
        BONUS_W_PERCENTAGE_20
    );

    public static final CabinBonus CABIN_BONUS_J = new CabinBonus(
            null,
            "Club World",
            CABIN_CODE_J,
            BONUS_J_PERCENTAGE_50
    );

}
