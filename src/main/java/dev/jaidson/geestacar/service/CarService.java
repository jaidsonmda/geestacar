package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.dto.EventDTO;
import dev.jaidson.geestacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static dev.jaidson.geestacar.enums.EventType.ENTRY;
import static dev.jaidson.geestacar.enums.EventType.EXIT;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }
    public Optional<Car> findByLicensePlate(String licensePlate){
        return carRepository.findByLicensePlate(licensePlate);
    }
    public Car saveNewCar(EventDTO event) {
        Optional<Car> carOp = findByLicensePlate(event.getLicensePlate());
        if (!carOp.isPresent()) {
            Car save = save(Car.builder()
                    .make("Citröen")
                    .model("C3 AIRCROSS7")
                    .color("Ruby")
                    .inTheGarage(true)
                    .licensePlate(event.getLicensePlate()).build());

            System.out.println("Carro criado com sucesso!");
            return save;
        } else {
            Car car = carOp.get();
            if (EXIT == event.getEventType()) {
                car.setInTheGarage(false);
            }
            if (ENTRY == event.getEventType()) {
                car.setInTheGarage(true);
            }
            Car save = save(car);
            System.out.println("Carro atualizado com sucesso!");
            return save;
        }
    }
}
