package dev.jaidson.geestacar.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_register")
@Data
@NoArgsConstructor
public class EventRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "car_id")
    public Car car;
    @ManyToOne
    @JoinColumn(name = "spot_id", nullable = true)
    public Spot spot;
}
