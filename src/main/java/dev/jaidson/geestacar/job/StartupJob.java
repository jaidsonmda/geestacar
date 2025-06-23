package dev.jaidson.geestacar.job;

import dev.jaidson.geestacar.dto.DataGarageDTO;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StartupJob {

    private final WebClient webClient;

    public StartupJob(WebClient webClient) {
        this.webClient = webClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        this.webClient.get()
                .uri("/garage")
                .retrieve()
                .bodyToMono(DataGarageDTO.class)
                .block()
    }
}
