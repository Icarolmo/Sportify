package org.sportify.SportifyApplication.service;

import lombok.extern.slf4j.Slf4j;
import org.sportify.SportifyApplication.domain.Event;
import org.sportify.SportifyApplication.dto.EventDTO;
import org.sportify.SportifyApplication.enums.StatusEnum;
import org.sportify.SportifyApplication.exception.EventAlreadyExistsException;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.exception.EventNotExistsException;
import org.sportify.SportifyApplication.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class EventService {

    @Autowired
    EventRepository repository;

    private final HashMap<String, String> allowedTypes = new HashMap<String, String>(10) {{
        put("FUTEBOL",  "");
        put("BASQUETE", "");
        put("VOLEI",    "");
        put("HANDEBOL", "");
        put("PINGPONG", "");
        put("FUTEMESA", "");
        put("CORRIDA",  "");
    }};

    public void createEvent(EventDTO eventDTO)
    {
        Event newEvent = new Event(eventDTO, StatusEnum.ACTIVE);

        Optional<Event> eventAlreadyExists = repository.findByActivityTitle(newEvent.getActivityTitle());
        if (eventAlreadyExists.isPresent()){
            throw new EventAlreadyExistsException(newEvent.getActivityTitle() + ": evento com este nome já existente. Escolha outro nome para o event.");
        }

        if(!allowedTypes.containsKey(newEvent.getType())) {
            throw new RequestBodyWithIncorrectDataException(newEvent.getType() + ": tipo de evento inválido, escolha um tipo válido. tipos válidos: FUTEBOL, BASQUETE, VOLEI, HANDEBOL, PINGPONG,FUTEMESA E CORRIDA");
        }

        repository.save(newEvent);
    }

    public Collection<EventDTO> getEventAll() {
        List<Event> eventList = repository.findAll();

        return Event.EventEntityListForEventDTO(eventList);
    }

    public Collection<EventDTO> getEventByType(String type) {
        if(!allowedTypes.containsKey(type)) {
            throw new RequestBodyWithIncorrectDataException(type + ": tipo de evento inválido, escolha um tipo válido. tipos válidos: FUTEBOL, BASQUETE, VOLEI, HANDEBOL, PINGPONG,FUTEMESA E CORRIDA");
        }

        Optional<List<Event>> eventList = repository.findByType(type);
        return Event.EventEntityListForEventDTO(eventList.orElse(Collections.emptyList()));
    }

    public void deleteEvent(String actitivyTitle){
        Optional<Event> event = repository.findByActivityTitle(actitivyTitle);

        if(event.isEmpty()){
            log.info("event with activity_title: [{}] not exists. stopping process...", actitivyTitle);
            throw new EventNotExistsException("event with activity_title: " + actitivyTitle + " not exists");
        }

        log.info("trying to delete event {}", actitivyTitle);

        repository.delete(event.get());
    }

    public EventDTO updateEvent(EventDTO eventDTO){
        Event updatedEvent = new Event(eventDTO, StatusEnum.ACTIVE);

        Optional<Event> eventAlreadyExists = repository.findByActivityTitle(updatedEvent.getActivityTitle());
        if (eventAlreadyExists.isEmpty()){
            throw new EventAlreadyExistsException(updatedEvent.getActivityTitle() + ": não foi encontrado nenhum evento com este título.");
        }

        repository.save(updatedEvent);

        return eventDTO;
    }
}
