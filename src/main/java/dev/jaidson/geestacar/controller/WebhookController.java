package dev.jaidson.geestacar.controller;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.Event;
import dev.jaidson.geestacar.domain.RegisterEvent;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.mapper.EventMapper;
import dev.jaidson.geestacar.service.CarService;
import dev.jaidson.geestacar.service.RegisterEventService;
import dev.jaidson.geestacar.service.SpotService;
import dev.jaidson.geestacar.util.Calculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static dev.jaidson.geestacar.enums.EventType.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    @Autowired
    private CarService carService;
    @Autowired
    private SpotService spotService;
    @Autowired
    RegisterEventService registerEventService;
    @PostMapping
    public ResponseEntity<String> receberEvento(@RequestBody EventDTO event) {


        Car car = saveNewCar(event);
        switch (event.getEventType()) {
            case ENTRY -> entry(event, car);
            case EXIT -> exit(event, car);
            case PARKED -> parked(event, car);
            default -> System.out.println("Tipo de evento desconhecido: " + event.getEventType());
        }


        return ResponseEntity.ok("Evento recebido com sucesso.");
    }

    private Car saveNewCar(EventDTO event) {
        Optional<Car> carOp = carService.findByLicensePlate(event.getLicensePlate());
        if(!carOp.isPresent()){
            Car save = carService.save(Car.builder()
                    .make("Citröen")
                    .model("C3 AIRCROSS7")
                    .color("Ruby")
                    .inTheGarage(true)
                    .licensePlate(event.getLicensePlate()).build());

            System.out.println("Carro criado com sucesso!");
            return  save;
        }else{
            Car car = carOp.get();
            if(EXIT == event.getEventType() ){
               car.setInTheGarage(false);
           }
           if(ENTRY == event.getEventType() ){
               car.setInTheGarage(true);
           }
            Car save = carService.save(car);
            System.out.println("Carro atualizado com sucesso!");
            return  save;
       }
    }

    private ResponseEntity entry(EventDTO event, Car car) {
        int spotUnoccupied = spotService.findSpotUnoccupied();

        if(spotUnoccupied==0) {
            car.setInTheGarage(false);
            carService.save(car);
            System.out.println("Carro não entrou na garagem!");
            return ResponseEntity.status(409).build();

        }else {
            Event domain = EventMapper.toDomain(event);
            domain.setEntryTime(LocalDate.now());
            registerEventService
                    .save(RegisterEvent
                            .builder()
                            .event()
                            .licensePlate(car.getLicensePlate())
                            .car(car)
                            .entryTime(LocalDate.now()).build());
            System.out.println("Carro entrou da garagem! existem :"+spotUnoccupied+" vagas disponíveis");
        }
       return  ResponseEntity.ok("Evento recebido com sucesso.");

    }

    private ResponseEntity exit(EventDTO event, Car car) {
        List<RegisterEvent> registerEventList = registerEventService.findByLicensePlateAndExitTimeIsNull(event.getLicensePlate());
        if(!registerEventList.isEmpty()) {
            System.out.println("Carro saiu da garagem!");

            double perc = Calculate.percentage(spotService.findSpotOccupied(), spotService.countAllSpots());

            proccessExit(car,registerEventList);

            Event domain = EventMapper.toDomain(event);
            domain.setPriceUntilNow(Calculate.priceExit(perc, 20.00));

            System.out.println(perc);
            System.out.println();
            registerEventService
                    .save(RegisterEvent
                            .builder()
                            .event(domain)
                            .car(car)
                            .build());
            return ResponseEntity.ok("Evento recebido com sucesso.");
        }
        System.out.println("Sem evento de entrada para o carro "+car.getLicensePlate()+"!");
        return ResponseEntity.status(409).build();
    }

    private void proccessExit(Car car,List<RegisterEvent> registerEventList) {
                registerEventList.forEach(
                        registerEvent -> {
                            if( registerEvent.getEvent().getEventType()==PARKED ){
                                Spot spot =registerEvent.getSpot();
                                spot.setOccupied(false);
                                spotService.save(spot);
                            }
                            registerEvent.getEvent().setExitTime(LocalDate.now());
                            registerEventService.save(registerEvent);
                        }
                );
        car.setInTheGarage(false);
        carService.save(car);


    }

    private ResponseEntity parked(EventDTO event, Car car) {
        System.out.println("Carro estacionou!");
        int spotUnoccupied = spotService.findSpotUnoccupied();
        Event domain = EventMapper.toDomain(event);
        domain.setEntryTime(LocalDate.now());
        if(spotUnoccupied==0) {
            System.out.println("Carro não entrou da garagem!");
            return ResponseEntity.status(409).build();

        }else {
            Spot spot = spotService.findByLatAndLng(event.getLat(), event.getLng());
            spot.setOccupied(true);
            spotService.save(spot);
            registerEventService
                    .save(RegisterEvent
                            .builder()
                            .event(domain)
                            .car(car)

                            .spot(spot)
                            .entryTime(LocalDate.now()).build());
            return ResponseEntity.ok("Evento recebido com sucesso.");
        }
    }
}
