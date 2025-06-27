package dev.jaidson.geestacar.mapper;

import dev.jaidson.geestacar.domain.Event;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.dto.EventOutDTO;

import java.time.LocalTime;
import java.util.List;

public class EventMapper {

    public static Event toDomain(EventDTO dto) {
        return Event.builder()
                .priceUntilNow(0.0)
                .lat(dto.getLat())
                .eventType(dto.getEventType())
                .licensePlate(dto.getLicensePlate())
                .lng(dto.getLng())
                .build();
    }
    public static EventOutDTO toOutDto(Event domain) {
        EventOutDTO dto = new EventOutDTO();
        dto.setEventType(domain.getEventType());
        dto.setLat(domain.getLat());
        dto.setLng(domain.getLng());
        dto.setLicensePlate(domain.getLicensePlate());
        dto.setPriceUntilNow(domain.getPriceUntilNow());
        return dto;
    }
    public static EventDTO toDto(Event domain) {
        EventDTO dto = new EventDTO();
        dto.setEventType(domain.getEventType());
        dto.setLat(domain.getLat());
        dto.setLng(domain.getLng());
        dto.setLicensePlate(domain.getLicensePlate());
        return dto;
    }
    public static List<EventDTO> toDtoList(List<Event> domainList) {
        return domainList.stream().map(EventMapper::toDto).toList();
    }

    public static List<Event> toDomainList(List<EventDTO> dtoList) {
        return dtoList.stream().map(EventMapper::toDomain).toList();
    }
}
