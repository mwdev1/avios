package com.example.avios.component;

import com.example.avios.domain.model.CabinBonus;
import com.example.avios.domain.exception.ResourceNotFoundException;
import com.example.avios.domain.model.AviosFlightPoints;
import com.example.avios.domain.repo.CabinBonusRepository;
import com.example.avios.domain.service.CabinBonusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.example.avios.TestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class CabinBonusServiceTests {

    @Mock
    private CabinBonusRepository cabinBonusRepository;

    @InjectMocks
    private CabinBonusService cabinBonusService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    public void testCalculateBonusForAllCabins() {
        // Arrange
        List<CabinBonus> cabinBonuses = List.of(
                CABIN_BONUS_W,
                CABIN_BONUS_J
        );
        when(cabinBonusRepository.findAll()).thenReturn(cabinBonuses);

        // Act
        List<AviosFlightPoints> aviosPoints = cabinBonusService.calculateBonusForAllCabins(2000);

        // Assert
        assertThat(aviosPoints).hasSize(2);
        assertThat(aviosPoints.get(0).cabinCode()).isEqualTo(CABIN_CODE_W);
        assertThat(aviosPoints.get(0).flightPoints()).isEqualTo(getExpectedTotalAviosEarned(BONUS_W_PERCENTAGE_20, 2000));
        assertThat(aviosPoints.get(1).cabinCode()).isEqualTo(CABIN_CODE_J);
        assertThat(aviosPoints.get(1).flightPoints()).isEqualTo(getExpectedTotalAviosEarned(BONUS_J_PERCENTAGE_50, 2000));
    }

    public BigDecimal getExpectedTotalAviosEarned(BigDecimal bonusPercentage, Integer basePoints) {
        BigDecimal bonusMultiplier = bonusPercentage.add(BigDecimal.ONE);
        return BigDecimal.valueOf(basePoints).multiply(bonusMultiplier).setScale(2);
    }

    @Test
    public void testCalculateBonusForSpecificCabin() {
        // Arrange
        when(cabinBonusRepository.findByCabinCode(CABIN_CODE_W)).thenReturn(Optional.of(CABIN_BONUS_W));

        // Act
        AviosFlightPoints aviosPoints = cabinBonusService.calculateBonusForCabin(CABIN_CODE_W, 1000);

        // Assert
        assertThat(aviosPoints.cabinCode()).isEqualTo(CABIN_CODE_W);
        assertThat(aviosPoints.flightPoints()).isEqualTo(getExpectedTotalAviosEarned(BONUS_W_PERCENTAGE_20, 1000));
    }

    @Test
    public void testCalculateBonusForSpecificCabin_ThrowsException() {
        // Arrange
        String cabinCode = "X";
        when(cabinBonusRepository.findByCabinCode(cabinCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> cabinBonusService.calculateBonusForCabin(cabinCode, 3200));
    }
}
