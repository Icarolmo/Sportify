package org.sportify.SportifyApplication.service;

import jakarta.validation.constraints.Null;
import org.sportify.SportifyApplication.domain.Event;
import org.sportify.SportifyApplication.dto.ActitivyTitle;
import org.sportify.SportifyApplication.dto.EventDTO;
import org.sportify.SportifyApplication.enums.StatusEnum;
import org.sportify.SportifyApplication.exception.EventAlreadyExistsException;
import org.sportify.SportifyApplication.exception.EventBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public EventDTO createEvent(EventDTO eventDTO)
    {
        Event newEvent = new Event(eventDTO, StatusEnum.ACTIVE);
        newEvent.ValidateEvent();

        Optional<Event> eventAlreadyExists = repository.findByActivityTitle(newEvent.getActivityTitle());
        if (eventAlreadyExists.isPresent()){
            throw new EventAlreadyExistsException(newEvent.getActivityTitle() + ": evento com este nome já existente. Escolha outro nome para o event.");
        }

        if(!allowedTypes.containsKey(newEvent.getType())) {
            throw new EventBodyWithIncorrectDataException(newEvent.getType() + ": tipo de evento inválido, escolha um tipo válido. tipos válidos: FUTEBOL, BASQUETE, VOLEI, HANDEBOL, PINGPONG,FUTEMESA E CORRIDA");
        }

        repository.save(newEvent);
        return eventDTO;
    }

    public Collection<EventDTO> getEventAll() {
        List<Event> eventList = repository.findAll();
        return Event.EventEntityListForEventDTO(eventList);
    }

    public Collection<EventDTO> getEventByType(String type) {
        if(!allowedTypes.containsKey(type)) {
            throw new EventBodyWithIncorrectDataException(type + ": tipo de evento inválido, escolha um tipo válido. tipos válidos: FUTEBOL, BASQUETE, VOLEI, HANDEBOL, PINGPONG,FUTEMESA E CORRIDA");
        }

        Optional<List<Event>> eventList = repository.findByType(type);
        return Event.EventEntityListForEventDTO(eventList.orElse(Collections.emptyList()));
    }

    public EventDTO deleteEvent(ActitivyTitle actitivyTitle){
        Optional<Event> event = repository.findByActivityTitle(actitivyTitle.activity_title());

        if(event.isEmpty()){
            throw new EventAlreadyExistsException("EVENTO NÃO EXISTE MEU CHAPA");
        }

        repository.delete(event.get());

        return null;
    }

    public EventDTO updateEvent(EventDTO eventDTO){
        Event updatedEvent = new Event(eventDTO, StatusEnum.ACTIVE);
        updatedEvent.ValidateEvent();

        Optional<Event> eventAlreadyExists = repository.findByActivityTitle(updatedEvent.getActivityTitle());
        if (eventAlreadyExists.isEmpty()){
            throw new EventAlreadyExistsException(updatedEvent.getActivityTitle() + ": não foi encontrado nenhum evento com este título.");
        }

        repository.save(updatedEvent);

        return eventDTO;
    }
}
