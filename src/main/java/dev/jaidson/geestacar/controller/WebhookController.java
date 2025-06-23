package dev.jaidson.geestacar.controller;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.enums.EventType;
import dev.jaidson.geestacar.service.CarService;
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
    @PostMapping
    public ResponseEntity<String> receberEvento(@RequestBody EventDTO event) {
        System.out.println("Evento Recebido:");
        System.out.println("Placa : " + event.licensePlate);
        System.out.println("Tipo de Evento: " + event.eventType);
        saveNewCar(event);
        switch (event.eventType) {
            case ENTRY -> entry(event.eventType);
            case EXIT -> exit(event.eventType);
            case PARKED -> parked(event.eventType);
            default -> System.out.println("Tipo de evento desconhecido: " + event.eventType);
        }

        return ResponseEntity.ok("Evento recebido com sucesso.");
    }

    private void saveNewCar(EventDTO event) {
        Optional<Car> carOp = carService.findByLicensePlate(event.licensePlate);
        if(!carOp.isPresent()){
                carService.save(Car.builder().make("Citröen").model("C3 AIRCROSS7").color("Ruby").inTheGarage(true).licensePlate(event.licensePlate).build());
            System.out.println("Car criado com sucesso!");
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

    private void entry(EventType eventType) {
        System.out.println("Carro entrou na garagem!");
    }

    private void exit(EventType eventType) {
        System.out.println("Carro saiu da garagem!");
    }

    private void parked(EventType eventType) {
        System.out.println("Carro estacionou!");
    }
}
