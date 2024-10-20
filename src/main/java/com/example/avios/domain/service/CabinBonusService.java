package com.example.avios.domain.service;

import com.example.avios.domain.exception.ResourceNotFoundException;
import com.example.avios.domain.model.AviosFlightPoints;
import com.example.avios.domain.model.CabinBonus;
import com.example.avios.domain.repo.CabinBonusRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CabinBonusService {

    public CabinBonusService(CabinBonusRepository cabinBonusRepository) {
        this.cabinBonusRepository = cabinBonusRepository;
    }

    private CabinBonusRepository cabinBonusRepository;

    public List<AviosFlightPoints> calculateBonusForAllCabins(Integer basePoints) {
        return cabinBonusRepository.findAll().stream()
                .map(cb -> calculateBonusForCabin(cb, basePoints))
                .toList();
    }

    public AviosFlightPoints calculateBonusForCabin(String cabinCode, Integer basePoints) {

        return cabinBonusRepository.findByCabinCode(cabinCode.toUpperCase())
                .map(cb -> calculateBonusForCabin(cb, basePoints))
                .orElseThrow(() -> new ResourceNotFoundException("Cabin class not found for cabin code: " + cabinCode));
    }

    public AviosFlightPoints calculateBonusForCabin(CabinBonus cabinBonus, Integer basePoints) {
        BigDecimal bonusMultiplier = cabinBonus.getBonusProportion().add(BigDecimal.ONE);
        BigDecimal totalPointsEarned = BigDecimal.valueOf(basePoints).multiply(bonusMultiplier).setScale(2);
        return new AviosFlightPoints(cabinBonus.getCabinCode(), totalPointsEarned);
    }
}
