package dev.jaidson.geestacar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Spot;

import dev.jaidson.geestacar.filter.*;
import dev.jaidson.geestacar.service.CarService;
import dev.jaidson.geestacar.service.EventService;
import dev.jaidson.geestacar.service.SpotService;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static dev.jaidson.geestacar.enums.Sector.A;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.http.MediaType.APPLICATION_JSON;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParkingController.class)
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private CarService carService;

    @InjectMocks
    private SpotService spotService;

    @InjectMocks
    private EventService eventService;

    @Test
    void testPlateStatusFound() throws Exception {
        Car car = new Car();
        car.setLicensePlate("ABC-1234");

        Mockito.when(carService.findByLicensePlate("ABC-1234"))
                .thenReturn(Optional.of(car));

        FilterPlateStatus request = FilterPlateStatus.builder().licensePlate("ABC-1234").build();

        mockMvc.perform(post("/plate-status")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate").value("ABC-1234"));
    }

    @Test
    void testPlateStatusNotFound() throws Exception {
        Mockito.when(carService.findByLicensePlate("XYZ-9999"))
                .thenReturn(Optional.empty());

        FilterPlateStatus request = FilterPlateStatus.builder().licensePlate("XYZ-9999").build();

        mockMvc.perform(post("/plate-status")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSpotStatusFound() throws Exception {
        Spot spot = new Spot();
        spot.setLat(-23.5505);
        spot.setLng(-46.6333);

        Mockito.when(spotService.findByLatAndLng(-23.5505, -46.6333))
                .thenReturn(Optional.of(spot));

        FilterSpotStatus request = FilterSpotStatus.builder().lat(-23.5505).lng(-46.6333).build( );

        mockMvc.perform(post("/spot-status")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(-23.5505));
    }

    @Test
    void testSpotStatusNotFound() throws Exception {
        Mockito.when(spotService.findByLatAndLng(any(), any()))
                .thenReturn(Optional.empty());

        FilterSpotStatus request = FilterSpotStatus.builder().lat(0.0).lng(0.0).build();

        mockMvc.perform(post("/spot-status")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testRevenueFound() throws Exception {
        Spot spot = new Spot();
        spot.setSector(A);
        spot.setPriceUntilNow(10.0);

        Mockito.when(spotService.findAllBySector(eq(A), eq(LocalDate.of(2025, 6, 30))))
                .thenReturn(Collections.singletonList(spot));

        FilterRevenue request = FilterRevenue.builder().sector(A).date(LocalDate.of(2025, 6, 30)).build();

        mockMvc.perform(post("/revenue")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRevenue").value(10.0))
                .andExpect(jsonPath("$.currency").value("BRL"));
    }

    @Test
    void testRevenueInvalidSector() throws Exception {
        FilterRevenue request = FilterRevenue.builder().date(LocalDate.of(2025, 6, 30)).build() ;

        mockMvc.perform(post("/revenue")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}