package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Garage;
import dev.jaidson.geestacar.enums.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    Optional<Garage> findBySector(Sector sector);
}
