package dev.jaidson.geestacar.mapper;

import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.SpotDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SpotMapper {

    public static Spot toDomain(SpotDTO dto) {
        return Spot.builder()
                .id(dto.getId())
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

    public static List<Spot> toDomainList(List<SpotDTO> dtoList) {
        return dtoList.stream().map(SpotMapper::toDomain).collect(Collectors.toList());
    }

    public static List<SpotDTO> toDtoList(List<Spot> domainList) {
        return domainList.stream().map(SpotMapper::toDto).collect(Collectors.toList());
    }
}
