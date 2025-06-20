package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    public Optional<Car> findByLicensePlate(String licencePlate);
}
