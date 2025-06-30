package dev.jaidson.geestacar.mapper;

import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.RevenueDTO;
import dev.jaidson.geestacar.dto.SpotDTO;
import dev.jaidson.geestacar.dto.SpotOutDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SpotMapper {

    public static Spot toDomain(SpotDTO dto) {
        return Spot.builder()
                .sector(dto.getSector())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }

    public static SpotDTO toDto(Spot domain) {
        SpotDTO dto = new SpotDTO();
        dto.setId(domain.getId());
        dto.setSector(domain.getSector());
        dto.setLat(domain.getLat());
        dto.setLng(domain.getLng());
        return dto;
    }
    public static SpotOutDTO spotOutDTO(Spot domain) {
        SpotOutDTO dto = new SpotOutDTO();
        dto.setOccupied(domain.isOccupied());
        dto.setLicensePlate(domain.getLicensePlate());
        dto.setPriceUntilNow(domain.getPriceUntilNow());
        dto.setEntryTime(domain.getEntryTime());
        dto.setTimeParked(domain.getTimeParked());
        return dto;
    }
    public static RevenueDTO  revenueDTO(Double amount, String currency, LocalDate date){
        RevenueDTO dto = new RevenueDTO();
        dto.setAmount(amount);
        dto.setCurrency(currency);
        dto.setDate(date);
        return dto;
    }
    public static List<Spot> toDomainList(List<SpotDTO> dtoList) {
        return dtoList.stream().map(SpotMapper::toDomain).collect(Collectors.toList());
    }

    public static List<SpotDTO> toDtoList(List<Spot> domainList) {
        return domainList.stream().map(SpotMapper::toDto).collect(Collectors.toList());
    }
}
