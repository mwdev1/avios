package com.example.avios.integration;

import com.example.avios.application.dto.RouteAviosPointsResponse;
import com.example.avios.domain.repo.CabinBonusRepository;
import com.example.avios.domain.repo.FlightRouteRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.example.avios.TestBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DataApplicationTests {


  @Autowired
  MockMvc mvc;
  @Autowired
  CabinBonusRepository cabinBonusRepository;
  @Autowired
  FlightRouteRepository flightRouteRepository;
  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    cabinBonusRepository.save(CABIN_BONUS_W);
    flightRouteRepository.save(FLIGHT_ROUTE_LAX_LHR);
  }

  @Test
  public void shouldReturnAllLicensedMatchesByCustomer() throws Exception {
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/points/route/{routeId}", AIRPORT_CODE_LHR + "-" + AIRPORT_CODE_LAX)
                    .param("cabinCode", CABIN_CODE_W)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();

    RouteAviosPointsResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
    assertThat(response.departureAirportCode()).isEqualTo(AIRPORT_CODE_LHR);
    assertThat(response.arrivalAirportCode()).isEqualTo(AIRPORT_CODE_LAX);
    assertThat(response.flightPointsEarned()).hasSize(1);
    assertThat(response.flightPointsEarned().get(0).cabinCode()).isEqualTo(CABIN_CODE_W);
    assertThat(response.flightPointsEarned().get(0).flightPoints()).isEqualTo(BigDecimal.valueOf(1200).setScale(2));
  }

  @Test
  public void testNotFoundRequestForInvalidCabinCode() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/points/route/{routeId}", AIRPORT_CODE_LHR + "-" + AIRPORT_CODE_LAX)
                    .param("cabinCode", "INVALID_CABIN_CODE")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound());
  }

  @Test
  public void testBadRequestForInvalidRouteId() throws Exception {
    mvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/points/route/{routeId}", "LHR@££$^LAX")
                    .param("cabinCode", CABIN_CODE_W)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
  }

}
