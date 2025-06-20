package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Garage;
import dev.jaidson.geestacar.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GarageService {
    @Autowired
    private GarageRepository garageRepository;



    public List<Garage> findAll() {
        return garageRepository.findAll();
    }

    public Optional<Garage> findById(Long id) {
        return garageRepository.findById(id);
    }

    public Garage save(Garage garage) {
        return garageRepository.save(garage);
    }

    public void deleteById(Long id) {
        garageRepository.deleteById(id);
    }
}

