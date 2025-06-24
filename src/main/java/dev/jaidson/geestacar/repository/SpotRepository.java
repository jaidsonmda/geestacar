package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
 int countAllByOccupiedFalse();
 Spot findOneByOccupiedFalse();
 Spot findSpotByLatAndLng(Double lat, Double lng);
 }
