package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
}
