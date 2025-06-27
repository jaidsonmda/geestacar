package dev.jaidson.geestacar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventOutDTO extends EventDTO {
    @JsonProperty("price_until_now")
    private Double priceUntilNow;
    @JsonProperty("time_parked")
    private LocalDate timeParked;
}
