package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Garage;
import dev.jaidson.geestacar.enums.Sector;
import dev.jaidson.geestacar.repository.GarageRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Garage save(Garage garage) {

        return garageRepository.save(garage);
    }
    @Transactional
    public void saveAll(List<Garage> garages) {
        garageRepository.saveAll(garages);
    }

    @Transactional
    public void deleteById(Long id) {
        garageRepository.deleteById(id);
    }
    public Optional<Garage> findGarageBySector(Sector sector){
        return garageRepository.findBySector(sector);
    }
    public double findBasePriceBySector(Sector sector) {
       return findGarageBySector(sector).get().getBasePrice();
    }
}

