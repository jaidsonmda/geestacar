package dev.jaidson.geestacar.domain;

import dev.jaidson.geestacar.enums.Sector;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "spot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Sector sector;
    private double lat;
    private Double priceUntilNow=0.0;
    private String licensePlate;
    private LocalDate entryTime;
    private LocalDate timeParked;
    private double lng;
    @NotNull
    private boolean occupied = false;
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> event;
}
