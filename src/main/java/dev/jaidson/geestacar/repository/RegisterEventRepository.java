package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.RegisterEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterEventRepository  extends JpaRepository<RegisterEvent, Long> {

}
