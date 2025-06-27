package dev.jaidson.geestacar.domain;


import dev.jaidson.geestacar.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "event_register")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
    @ManyToOne
    @JoinColumn(name = "spot_id", nullable = true)
    private Spot spot;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
