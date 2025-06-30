package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Event;
import dev.jaidson.geestacar.domain.Spot;
import dev.jaidson.geestacar.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findBySpotAndEventType(Spot spot, EventType eventType);
}
