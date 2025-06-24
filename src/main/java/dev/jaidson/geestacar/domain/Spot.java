package dev.jaidson.geestacar.domain;

import dev.jaidson.geestacar.enums.Sector;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private double lng;
    private boolean occupied = false;
}
