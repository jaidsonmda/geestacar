package dev.jaidson.geestacar.job;

import dev.jaidson.geestacar.dto.DataGarageDTO;
import dev.jaidson.geestacar.dto.GarageDTO;
import dev.jaidson.geestacar.dto.SpotDTO;
import dev.jaidson.geestacar.mapper.GarageMapper;
import dev.jaidson.geestacar.mapper.SpotMapper;
import dev.jaidson.geestacar.service.GarageService;
import dev.jaidson.geestacar.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class StartupJob {
    @Autowired
    WebClient webClient;
    @Autowired
    SpotService spotService;
    @Autowired
    GarageService garageService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        DataGarageDTO dataGarageDTO = this.webClient.get()
                .uri("/garage")
                .retrieve()
                .bodyToMono(DataGarageDTO.class)
                .block();
        saveGarage(dataGarageDTO.getGarage());
        saveSpots(dataGarageDTO.getSpots());
        System.out.println("Dados de Garage: " + dataGarageDTO.getSpots());
    }
    private void saveGarage(List<GarageDTO> garages) {
       garageService.saveAll(GarageMapper.toDomainList(garages));
    }

    private void saveSpots(List<SpotDTO> spots) {
       spotService.saveAll(SpotMapper.toDomainList(spots));
    }
}
