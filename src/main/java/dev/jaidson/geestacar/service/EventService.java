package dev.jaidson.geestacar.service;

import dev.jaidson.geestacar.domain.Event;
import dev.jaidson.geestacar.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Event save(Event registerEvent) {
        return eventRepository.save(registerEvent);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }


}
