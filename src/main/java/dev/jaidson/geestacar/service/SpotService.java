package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.repository.SpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
    @Transactional
    public Spot save(Spot spot) {

            return spotRepository.save(spot);



    }
    @Transactional
    public void saveAll(List<Spot> spots) {
        try {
            spotRepository.saveAll(spots);
        }
        catch (ObjectOptimisticLockingFailureException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteById(Long id) {
        spotRepository.deleteById(id);
    }
}
