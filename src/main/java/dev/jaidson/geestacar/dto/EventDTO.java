package dev.jaidson.geestacar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaidson.geestacar.enums.EventType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {
    @JsonProperty("license_plate")
    private String licensePlate;
    @JsonProperty("entry_time")
    private LocalDate entryTime;
    @JsonProperty("event_type")
    private EventType eventType;
    @JsonProperty("exit_time")
    private LocalDate exitTime;


    private Double lat;
    private Double lng;
}
