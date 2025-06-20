package dev.jaidson.geestacar.dto;

import dev.jaidson.geestacar.enums.Sector;
import lombok.Data;

@Data
public class SpotDTO {

    private long id;
    private Sector sector;
    private double lat;
    private double lng;
    private boolean occupied = false;
}
