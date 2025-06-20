package dev.jaidson.geestacar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaidson.geestacar.enums.Sector;
import lombok.Data;

@Data
public class GarageDTO {

    private Sector sector;

    @JsonProperty("basePrice")
    private double basePrice;

    @JsonProperty("max_capacity")
    private int maxCapacity;

    @JsonProperty("open_hour")
    private String openHour;

    @JsonProperty("close_hour")
    private String closeHour;

    @JsonProperty("duration_limit_minutes")
    private int durationLimitMinutes;
}
