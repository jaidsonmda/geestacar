package dev.jaidson.geestacar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "car")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String licensePlate;
    private String model;
    private String color;
    private int year;
    private String make;
    private boolean inTheGarage;
    private Double priceUntilNow=0.0;
    private LocalDate timeParked;
    private LocalDate entryTime;
    private LocalDate exitTime;


    private double lat;
    private double lng;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> event;
}
