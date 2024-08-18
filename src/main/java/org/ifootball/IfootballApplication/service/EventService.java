package org.ifootball.IfootballApplication.service;

import org.ifootball.IfootballApplication.dto.EventDTO;
import org.ifootball.IfootballApplication.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository repository;

    public List<EventDTO> getEventAll() {
        return EventDTO.EventListForEventDTOList(repository.findAll());
    }

    public List<EventDTO> getEventByType(String type) {
        return EventDTO.EventListForEventDTOList(repository.findByType(type));
    }
}
