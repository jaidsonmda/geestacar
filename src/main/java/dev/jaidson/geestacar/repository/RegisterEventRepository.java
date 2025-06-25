package dev.jaidson.geestacar.repository;

import dev.jaidson.geestacar.domain.Car;
import dev.jaidson.geestacar.domain.RegisterEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterEventRepository  extends JpaRepository<RegisterEvent, Long> {
    List<RegisterEvent> findByCarOrderByIdDesc(Car car);

}
