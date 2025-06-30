package dev.jaidson.geestacar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SpotOutDTO {
    private boolean occupied;
    @JsonProperty("price_until_now")
    private Double priceUntilNow;
    @JsonProperty("time_parked")
    private LocalDate timeParked;
    @JsonProperty("entry_time")
    private LocalDate entryTime;
    @JsonProperty("license_plate")
    private String licensePlate;


}
