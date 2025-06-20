package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SpotService {
    @Autowired
    private SpotRepository spotRepository;
    public List<Spot> findAll() {
        return spotRepository.findAll();
    }

    public Optional<Spot> findById(Long id) {
        return spotRepository.findById(id);
    }

    public Spot save(Spot spot) {
        return spotRepository.save(spot);
    }

    public void deleteById(Long id) {
        spotRepository.deleteById(id);
    }
}
