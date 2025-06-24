package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.EventRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterEventRepository  extends JpaRepository<EventRegister, Long> {
}
