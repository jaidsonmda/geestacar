package dev.jaidson.geestacar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaidson.geestacar.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garage_seq")
    private Long id;

    private String licensePlate;

    private LocalDate entryTime;

    private EventType eventType;
    private LocalDate exitTime;
    private Double priceUntilNow;
    private Double lat;
    private Double lng;
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegisterEvent> eventRegister;
}
