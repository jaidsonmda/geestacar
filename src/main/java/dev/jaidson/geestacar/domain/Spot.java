package dev.jaidson.geestacar.domain;

import dev.jaidson.geestacar.enums.Sector;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private double lng;
    @NotNull
    private boolean occupied = false;
    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegisterEvent> eventRegister;
}
