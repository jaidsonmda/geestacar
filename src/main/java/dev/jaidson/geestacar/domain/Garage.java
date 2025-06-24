package dev.jaidson.geestacar.domain;

import dev.jaidson.geestacar.enums.Sector;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "garage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Garage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garage_seq")
    private Long id;

    private Sector sector;
    private double basePrice;
    private int maxCapacity;
    private LocalTime openHour;
    private LocalTime closeHour;
    private int durationLimitMinutes;
}
