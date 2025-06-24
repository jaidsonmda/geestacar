package dev.jaidson.geestacar.controller;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.enums.EventType;
import dev.jaidson.geestacar.service.CarService;
import dev.jaidson.geestacar.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static dev.jaidson.geestacar.enums.EventType.ENTRY;
import static dev.jaidson.geestacar.enums.EventType.EXIT;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    @Autowired
    private CarService carService;
    @Autowired
    private SpotService spotService;
    @PostMapping
    public ResponseEntity<String> receberEvento(@RequestBody EventDTO event) {

        saveNewCar(event);
        switch (event.eventType) {
            case ENTRY -> entry(event);
            case EXIT -> exit(event);
            case PARKED -> parked(event);
            default -> System.out.println("Tipo de evento desconhecido: " + event.eventType);
        }


        return ResponseEntity.ok("Evento recebido com sucesso.");}

    private void saveNewCar(EventDTO event) {
        Optional<Car> carOp = carService.findByLicensePlate(event.licensePlate);
        if(!carOp.isPresent()){
                carService.save(Car.builder()
                        .make("Citröen")
                        .model("C3 AIRCROSS7")
                        .color("Ruby")
                        .inTheGarage(true)
                        .licensePlate(event.licensePlate).build());
            System.out.println("Carro criado com sucesso!");
        }else{
            Car car = carOp.get();
            if(EXIT == event.eventType ){
               car.setInTheGarage(false);
           }
           if(ENTRY == event.eventType ){
               car.setInTheGarage(true);
           }
           carService.save(car);
            System.out.println("Carro atualizado com sucesso!");
       }
    }

    private ResponseEntity entry(EventDTO event) {
        int spotUnoccupied = spotService.findSpotUnoccupied();
        if(spotUnoccupied==0) return ResponseEntity.status(409).build();
        return ResponseEntity.status(409).build();// ResponseEntity.ok("Evento recebido com sucesso.");

    }

    private ResponseEntity exit(EventDTO event) {
        System.out.println("Carro saiu da garagem!");
        return ResponseEntity.ok("Evento recebido com sucesso.");
    }

    private ResponseEntity parked(EventDTO event) {
        System.out.println("Carro estacionou!");
        Spot spot = spotService.findByLatAndLng(event.lat, event.lng);
        spot.setOccupied(true);
        spotService.save(spot);
        return ResponseEntity.ok("Evento recebido com sucesso.");
    }
}
