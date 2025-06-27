package dev.jaidson.geestacar.dto;

import dev.jaidson.geestacar.domain.Garage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DataGarageDTO {
    private List<GarageDTO> garage = new ArrayList<>();
    private List<SpotDTO> spots = new ArrayList<>();
}
