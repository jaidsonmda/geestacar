package dev.jaidson.geestacar.mapper;

import dev.jaidson.geestacar.domain.Garage;
import dev.jaidson.geestacar.dto.GarageDTO;

import java.time.LocalTime;
import java.util.List;


import static dev.jaidson.geestacar.enums.Sector.A;

public class GarageMapper {

    public static Garage toDomain(GarageDTO dto) {
        return Garage.builder()
                .sector(dto.getSector())
                .basePrice(dto.getSector()==A?25:20)
                .maxCapacity(dto.getMaxCapacity())
                .openHour(LocalTime.parse(dto.getOpenHour()))
                .closeHour(LocalTime.parse(dto.getCloseHour()))
                .durationLimitMinutes(dto.getDurationLimitMinutes())
                .build();
    }

    public static GarageDTO toDto(Garage domain) {
        GarageDTO dto = new GarageDTO();
        dto.setSector(domain.getSector());
        dto.setBasePrice(domain.getBasePrice());
        dto.setMaxCapacity(domain.getMaxCapacity());
        dto.setOpenHour(domain.getOpenHour().toString());     // format: HH:mm
        dto.setCloseHour(domain.getCloseHour().toString());   // format: HH:mm
        dto.setDurationLimitMinutes(domain.getDurationLimitMinutes());
        return dto;
    }
    public static List<GarageDTO> toDtoList(List<Garage> domainList) {
        return domainList.stream().map(GarageMapper::toDto).toList();
    }

    public static List<Garage> toDomainList(List<GarageDTO> dtoList) {
        return dtoList.stream().map(GarageMapper::toDomain).toList();
    }
}
