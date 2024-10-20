package com.example.avios.component;

import com.example.avios.domain.model.AviosFlightPoints;
import com.example.avios.domain.repo.CabinBonusRepository;
import com.example.avios.domain.repo.FlightRouteRepository;
import com.example.avios.domain.service.CabinBonusService;
import com.example.avios.domain.service.FlightRouteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.example.avios.TestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FlightRouteServiceTests {

    @Autowired
    private FlightRouteRepository flightRouteRepository;
    @Autowired
    private CabinBonusRepository cabinBonusRepository;

    @Autowired
    private CabinBonusService cabinBonusService;

    @Autowired
    private FlightRouteService flightRouteService;


    @Test
    public void testCalculateAviosForCabinBonus() {
        // Arrange
        flightRouteRepository.save(FLIGHT_ROUTE_LAX_LHR);
        cabinBonusRepository.save(CABIN_BONUS_W);

        // Act
        AviosFlightPoints aviosPoints = flightRouteService.calculateAviosForCabinCode(AIRPORT_CODE_LAX, AIRPORT_CODE_LHR, CABIN_CODE_W);

        // Assert
        assertThat(aviosPoints.cabinCode()).isEqualTo(CABIN_CODE_W);
        assertThat(aviosPoints.flightPoints()).isEqualTo(BigDecimal.valueOf(1200).setScale(2));
    }

    @Test
    public void testAirportCodesInterchangebility() {
        // Arrange
        flightRouteRepository.save(FLIGHT_ROUTE_LAX_LHR);
        cabinBonusRepository.save(CABIN_BONUS_W);

        // Act
        AviosFlightPoints aviosPoints1 = flightRouteService.calculateAviosForCabinCode(AIRPORT_CODE_LAX, AIRPORT_CODE_LHR, CABIN_CODE_W);
        AviosFlightPoints aviosPoints2 = flightRouteService.calculateAviosForCabinCode(AIRPORT_CODE_LHR, AIRPORT_CODE_LAX, CABIN_CODE_W);

        // Assert
        assertThat(aviosPoints1.cabinCode()).isEqualTo(CABIN_CODE_W);
        assertThat(aviosPoints1.flightPoints()).isEqualTo(BigDecimal.valueOf(1200).setScale(2));
        assertThat(aviosPoints2.cabinCode()).isEqualTo(CABIN_CODE_W);
        assertThat(aviosPoints2.flightPoints()).isEqualTo(BigDecimal.valueOf(1200).setScale(2));
    }

    @Test
    public void testCalculateAviosForNotDefinedRoute_WithDefaultAviosRouteBasePoints() {
        // Arrange
        cabinBonusRepository.save(CABIN_BONUS_W);

        // Act
        AviosFlightPoints aviosPoints1 = flightRouteService.calculateAviosForCabinCode("NOT_DEF_1", "NOT_DEF_2", CABIN_CODE_W);

        // Assert
        assertThat(aviosPoints1.cabinCode()).isEqualTo(CABIN_CODE_W);
        assertThat(aviosPoints1.flightPoints()).isEqualTo(BigDecimal.valueOf(600).setScale(2));
    }

}
