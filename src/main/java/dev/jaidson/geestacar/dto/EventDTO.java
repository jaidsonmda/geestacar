package dev.jaidson.geestacar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaidson.geestacar.enums.EventType;
import lombok.Data;

@Data
public class EventDTO {
    @JsonProperty("license_plate")
    private String licensePlate;
    @JsonProperty("entry_time")
    private String entryTime;
    @JsonProperty("event_type")
    private EventType eventType;
    @JsonProperty("exit_time")
    private String exitTime;


    private Double lat;
    private Double lng;
}
