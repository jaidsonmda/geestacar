package dev.jaidson.geestacar.dto;

import dev.jaidson.geestacar.domain.Garage;

import java.util.ArrayList;
import java.util.List;

public class DataGarageDTO {
    public List<GarageDTO> garage = new ArrayList<>();
    public List<SpotDTO> spots = new ArrayList<>();
}
