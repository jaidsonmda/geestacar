package dev.jaidson.geestacar.domain;


import dev.jaidson.geestacar.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_register")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_event_seq")
    private Long id;
    private String licensePlate;
    private LocalDate exitTime;


    @OneToMany(mappedBy = "registerEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> eventList = new ArrayList<>();

}
