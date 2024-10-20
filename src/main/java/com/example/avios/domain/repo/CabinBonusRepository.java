package com.example.avios.domain.repo;

import com.example.avios.domain.model.CabinBonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CabinBonusRepository extends JpaRepository<CabinBonus, Long> {

    Optional<CabinBonus> findByCabinCode(String cabinCode);
}