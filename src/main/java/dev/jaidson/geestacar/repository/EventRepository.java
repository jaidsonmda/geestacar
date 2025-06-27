package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
