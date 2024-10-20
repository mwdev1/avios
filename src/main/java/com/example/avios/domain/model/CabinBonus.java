package com.example.avios.domain.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "CabinBonus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CabinBonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cabin_name", length = 50, nullable = false)
    private String cabinName;

    @Column(name = "cabin_code", length = 1, nullable = false)
    private String cabinCode;

    @Column(name = "bonus_proportion", nullable = false)
    private BigDecimal bonusProportion;

}