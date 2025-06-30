package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.enums.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
 int countAllByOccupiedFalse();
 int countAllByOccupiedTrue();
 Spot findOneByOccupiedFalse();
 Optional<Spot> findSpotByLatAndLng(Double lat, Double lng);
 List<Spot> findAllBySectorAndTimeParked(Sector sector, LocalDate date);
 }
