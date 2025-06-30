package dev.jaidson.geestacar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.jaidson.geestacar.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    private Long id;

    private String licensePlate;

    private LocalDate entryTime;

    private EventType eventType;
    private LocalDate exitTime;
    private LocalDate parkedTime;
    private Double priceUntilNow;
    private Double lat;
    private Double lng;
    @ManyToOne
    @JoinColumn(name = "spot_id", nullable = true)
    private Spot spot;
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "register_event_id", nullable = false)
    private RegisterEvent registerEvent;
}
