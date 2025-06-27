package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.RegisterEvent;
import dev.jaidson.geestacar.repository.RegisterEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;
@Service
public class RegisterEventService {
    @Autowired
    private RegisterEventRepository registerEventRepository;

    public List<RegisterEvent> findAll() {
        return registerEventRepository.findAll();
    }

    public Optional<RegisterEvent> findById(Long id) {
        return registerEventRepository.findById(id);
    }

    public RegisterEvent save(RegisterEvent registerEvent) {
        return registerEventRepository.save(registerEvent);
    }

    public void deleteById(Long id) {
        registerEventRepository.deleteById(id);
    }

    public Optional<RegisterEvent> findRegisterByCarAndExitTimeNull() {
       return registerEventRepository.findFirstBySpotNotNullAndAndExitTimeIsNull();

    }
    public List<RegisterEvent> findByLicensePlateAndExitTimeIsNull(String licensePlate) {
        return registerEventRepository.findByLicensePlateAndExitTimeIsNull(licensePlate);

    }


}
