package dev.jaidson.geestacar.controller;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Event;
import dev.jaidson.geestacar.domain.RegisterEvent;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.mapper.EventMapper;
import dev.jaidson.geestacar.repository.EventRepository;
import dev.jaidson.geestacar.service.*;
import dev.jaidson.geestacar.util.Calculate;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.jaidson.geestacar.enums.EventType.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @Autowired
    private CarService carService;
    @Autowired
    private ParkingService parkingService;

    @PostMapping
    public ResponseEntity<String> receberEvento(@RequestBody EventDTO event) {


        Car car = carService.saveNewCar(event);
        switch (event.getEventType()) {
            case ENTRY -> parkingService.entry(event, car);
            case EXIT -> parkingService.exit(event, car);
            case PARKED -> parkingService.parked(event, car);
            default -> System.out.println("Tipo de evento desconhecido: " + event.getEventType());
        }


        return ResponseEntity.ok("Evento recebido com sucesso.");
    }




}
