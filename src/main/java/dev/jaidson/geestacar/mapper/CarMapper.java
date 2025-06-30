package dev.jaidson.geestacar.mapper;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.CarDTO;
import dev.jaidson.geestacar.dto.SpotOutDTO;

public class CarMapper {
    public static CarDTO carDTO(Car domain) {
        CarDTO dto = new CarDTO();

        dto.setLicensePlate(domain.getLicensePlate());
        dto.setPriceUntilNow(domain.getPriceUntilNow());
        dto.setEntryTime(domain.getEntryTime());
        dto.setTimeParked(domain.getTimeParked());
        dto.setLng(domain.getLng());
        dto.setLat(domain.getLat());
        return dto;
    }
}
