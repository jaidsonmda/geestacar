package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}
