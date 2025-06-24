package dev.jaidson.geestacar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaidson.geestacar.enums.EventType;

public class EventDTO {
    @JsonProperty("license_plate")
    public String licensePlate;
    @JsonProperty("entry_time")
    public String entryTime;
    @JsonProperty("event_type")
    public EventType eventType;
    @JsonProperty("exit_time")
    public String exitTime;

    public Double lat;
    public Double lng;
}
