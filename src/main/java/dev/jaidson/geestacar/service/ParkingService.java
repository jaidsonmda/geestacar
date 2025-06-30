package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Event;
import dev.jaidson.geestacar.domain.RegisterEvent;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.mapper.EventMapper;
import dev.jaidson.geestacar.util.Calculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static dev.jaidson.geestacar.enums.EventType.PARKED;
@Service
public class ParkingService {
    @Autowired
    private SpotService spotService;
    @Autowired
    private RegisterEventService registerEventService;
    @Autowired
    private EventService eventService;
    @Autowired
    private CarService carService;
    @Autowired
    private GarageService garageService;

    public ResponseEntity entry(EventDTO eventDTO, Car car) {
        int spotUnoccupied = spotService.findSpotUnoccupied();

        if (spotUnoccupied == 0) {
            car.setInTheGarage(false);
            carService.save(car);
            System.out.println("Carro não entrou na garagem!");
            return ResponseEntity.status(409).build();

        } else {
            updateEntitiesForEntry(eventDTO, car);
            System.out.println("Carro entrou da garagem! existem :" + spotUnoccupied + " vagas disponíveis");
        }
        return ResponseEntity.ok("Evento recebido com sucesso.");

    }

    private void updateEntitiesForEntry(EventDTO eventDTO, Car car) {
        car.setEntryTime(LocalDate.now());
        Event event = EventMapper.toDomain(eventDTO);
        event.setEntryTime(LocalDate.now());
        event.setCar(car);

        RegisterEvent registerEvent = RegisterEvent.builder()
                .licensePlate(event.getLicensePlate())
                .build();
        event.setRegisterEvent(registerEvent);
        eventService.save(event);
    }

    public ResponseEntity exit(EventDTO eventDTO, Car car) {
        Optional<RegisterEvent> registerEventOpt = registerEventService.findByLicensePlateAndExitTimeIsNull(eventDTO.getLicensePlate());
        if (registerEventOpt.isPresent()) {
            RegisterEvent registerEvent = registerEventOpt.get();
            
            processExit(car, registerEvent, eventDTO);

            System.out.println("Carro saiu da garagem!");
            
            return ResponseEntity.ok("Evento recebido com sucesso.");
        }
        System.out.println("Sem evento de entrada para o carro " + car.getLicensePlate() + "!");
        return ResponseEntity.status(409).build();
    }

    public ResponseEntity parked(EventDTO eventDTO, Car car) {
        Optional<RegisterEvent> registerEventOpt = registerEventService.findByLicensePlateAndExitTimeIsNull(eventDTO.getLicensePlate());
        if (registerEventOpt.isPresent()) {
            RegisterEvent registerEvent = registerEventOpt.get();
            updateEntitiesForParked(registerEvent,eventDTO, car);
            
            return ResponseEntity.ok("Evento recebido com sucesso.");

        } else {
            System.out.println("Carro não entrou da garagem!");
            return ResponseEntity.status(409).build();
        }
    }
    
    private void updateEntitiesForParked(RegisterEvent registerEvent, EventDTO eventDTO, Car car) {
        Optional<Spot> spot = spotService.findByLatAndLng(eventDTO.getLat(), eventDTO.getLng());
        spot.get().setOccupied(true);
        spot.get().setEntryTime(registerEvent.getEventList().get(0).getEntryTime());
        spot.get().setTimeParked(LocalDate.now());
        spot.get().setLicensePlate(registerEvent.getLicensePlate());

        car.setLat(eventDTO.getLat());
        car.setLng(eventDTO.getLng());
        car.setTimeParked(LocalDate.now());

        Event event = EventMapper.toDomain(eventDTO);
        event.setParkedTime(LocalDate.now());
        event.setSpot(spotService.save(spot.orElse(null)));
        event.setCar(car);
        event.setRegisterEvent(registerEvent);
        eventService.save(event);
    }
    
    private void processExit(Car car, RegisterEvent registerEvent, EventDTO eventDTO) {




        double exitPrice = calculateExitPrice(getBasePrice(registerEvent));
        vacateSpotForRegister(registerEvent,exitPrice);
        updateEntitiesForExit(car, registerEvent, eventDTO, exitPrice);
    }

    private void vacateSpotForRegister(RegisterEvent registerEvent, double exitPrice) {
        getEventParked(registerEvent)
                .findFirst()
                .ifPresent(event -> {
                    Spot spot = event.getSpot();
                    spot.setOccupied(false);
                    spot.setLicensePlate("");
                    spot.setPriceUntilNow(Objects.isNull(spot.getPriceUntilNow())?exitPrice:spot.getPriceUntilNow()+exitPrice);
                    spotService.save(spot);
                });
    }

    private static Stream<Event> getEventParked(RegisterEvent registerEvent) {
        return registerEvent.getEventList().stream()
                .filter(event -> event.getEventType() == PARKED);
    }

    private double calculateExitPrice(Double basePrice) {

        double occupationPercentage = Calculate.percentage(
                spotService.findSpotOccupied(),
                spotService.countAllSpots()
        );
        return Calculate.priceExit(occupationPercentage, basePrice);
    }

    private Double getBasePrice(RegisterEvent registerEvent) {
        Event event = getEventParked(registerEvent).findFirst().get();
       return garageService.findBasePriceBySector(event.getSpot().getSector());
    }

    private void updateEntitiesForExit(Car car, RegisterEvent registerEvent, EventDTO eventDTO, double price) {

        registerEvent.setExitTime(LocalDate.now());
        Event eventParked = registerEvent.getEventList().get(1);

        Event exitEvent = EventMapper.toDomain(eventDTO);
        exitEvent.setExitTime(LocalDate.now());
        exitEvent.setPriceUntilNow(price);
        exitEvent.setCar(car);
        exitEvent.setSpot(eventParked.getSpot());
        exitEvent.setRegisterEvent(registerEvent);


        car.setInTheGarage(false);
        car.setPriceUntilNow(Objects.isNull(car.getPriceUntilNow())?price: car.getPriceUntilNow()+price);
        car.setExitTime(LocalDate.now());


        carService.save(car);
        eventService.save(exitEvent);

    }
    
}
