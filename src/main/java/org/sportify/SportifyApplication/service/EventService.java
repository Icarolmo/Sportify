package org.sportify.SportifyApplication.service;

import lombok.extern.slf4j.Slf4j;
import org.sportify.SportifyApplication.domain.Event;
import org.sportify.SportifyApplication.domain.User;
import org.sportify.SportifyApplication.dto.EventDTO;
import org.sportify.SportifyApplication.enums.StatusEnum;
import org.sportify.SportifyApplication.exception.EventAlreadyExistsException;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;
import org.sportify.SportifyApplication.exception.EventNotExistsException;
import org.sportify.SportifyApplication.exception.UserAlreadySubscribeException;
import org.sportify.SportifyApplication.repository.EventRepository;
import org.sportify.SportifyApplication.repository.UsersSubscribeEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UsersSubscribeEventRepository usersSubscribeEventRepository;

    private final HashMap<String, String> allowedTypes = new HashMap<String, String>(10) {{
        put("FUTEBOL",  "");
        put("BASQUETE", "");
        put("VOLEI",    "");
        put("HANDEBOL", "");
        put("PINGPONG", "");
        put("FUTEMESA", "");
        put("CORRIDA",  "");
    }};

    private final HashMap<String, String> allowedLocalizations = new HashMap<String, String>(10) {{
        put("QUADRA-INTERNA-1",  "");
        put("QUADRA-INTERNA-2",  "");
        put("QUADRA-EXTERNA", "");
        put("PISTA",    "");
        put("MESAS-PINGPONG", "");
        put("MESAS-FUTEMESA", "");
        put("TATAME", "");
        put("PAREDE-ESCALADA",  "");
        put("APARELHOS-MUSCULACAO",  "");
    }};


    public void subscribeEvent(String activityTitle, User user){
        var userAlreadySubscribe = usersSubscribeEventRepository.findByActivityTitleAndUserEmail(activityTitle, user.getEmail()).isPresent();
        if (userAlreadySubscribe){
            throw new UserAlreadySubscribeException("usuário " + user.getEmail() + " já está inscrito no evento " + activityTitle);
        }

        var event = eventRepository.findByActivityTitleAndActiveAndAcceptSubscribes(activityTitle);
        if (event.isEmpty()){
            throw new EventNotExistsException(activityTitle + ": evento com este nome não existe, já encerrou ou não aceita mais inscrições.");
        }

        var eventSubscribe = event.get();
        var userSuscribeEvent = eventSubscribe.NewSubscriber(user.getEmail());

        eventRepository.save(eventSubscribe);
        usersSubscribeEventRepository.save(userSuscribeEvent);
    }

    public void createEvent(EventDTO eventDTO, User user)
    {
        var newEvent = new Event(eventDTO, StatusEnum.ACTIVE, user.getEmail());

        var eventAlreadyExists = eventRepository.findByActivityTitle(newEvent.getActivityTitle());
        if (eventAlreadyExists.isPresent()){
            throw new EventAlreadyExistsException(newEvent.getActivityTitle() + ": evento com este nome já existente. Escolha outro nome para o evento.");
        }

        if(!allowedTypes.containsKey(newEvent.getType())) {
            throw new RequestBodyWithIncorrectDataException(newEvent.getType() + ": tipo de evento inválido, escolha um tipo válido. tipos válidos: FUTEBOL, BASQUETE, VOLEI, HANDEBOL, PINGPONG,FUTEMESA E CORRIDA");
        }

        if(!allowedLocalizations.containsKey(newEvent.getLocalization())) {
            throw new RequestBodyWithIncorrectDataException(newEvent.getType() + ": localização de evento inválido, escolha um tipo válido.");
        }

        eventRepository.save(newEvent);
    }

    public Collection<EventDTO> getAllActiveEvents() {
        var eventList = eventRepository.findByStatus(1);
        return Event.EventEntityListForEventDTO(eventList.orElse(Collections.emptyList()));
    }

    public Collection<EventDTO> getEventByType(String type) {
        if(!allowedTypes.containsKey(type)) {
            throw new RequestBodyWithIncorrectDataException(type + ": tipo de evento inválido, escolha um tipo válido. tipos válidos: FUTEBOL, BASQUETE, VOLEI, HANDEBOL, PINGPONG,FUTEMESA E CORRIDA");
        }

        var eventList = eventRepository.findByType(type);
        return Event.EventEntityListForEventDTO(eventList.orElse(Collections.emptyList()));
    }

    public Collection<EventDTO> getEventByUser(User user){
        var allEventsByUser = eventRepository.findByCreatorMail(user.getEmail());

        return Event.EventEntityListForEventDTO(allEventsByUser.orElse(Collections.emptyList()));
    }

    public void deleteEvent(String actitivyTitle, User user){
        var event = eventRepository.findByActivityTitleAndCreatorMailAndStatus(actitivyTitle, user.getEmail(), StatusEnum.ACTIVE.getValue());

        if(event.isEmpty()){
            log.info("event with activity_title: [{}]. creator_user: [{}] not exists or do user is not creator.", actitivyTitle, user.getEmail());
            throw new EventNotExistsException("evento com activity_title: " + actitivyTitle + " não existe ou " + user.getEmail() + " não é o criador do evento.");
        }

        log.info("trying to delete event {}", actitivyTitle);

        eventRepository.delete(event.get());
    }

    public EventDTO updateEvent(EventDTO eventDTO, User user){
        if(!allowedTypes.containsKey(eventDTO.type())) {
            throw new RequestBodyWithIncorrectDataException(eventDTO.type() + ": tipo de evento inválido, escolha um tipo válido. tipos válidos: FUTEBOL, BASQUETE, VOLEI, HANDEBOL, PINGPONG,FUTEMESA E CORRIDA");
        }

        if(!allowedLocalizations.containsKey(eventDTO.localization())) {
            throw new RequestBodyWithIncorrectDataException(eventDTO.localization() + ": localização de evento inválido, escolha um tipo válido.");
        }

        var eventAlreadyExists = eventRepository.findByActivityTitleAndCreatorMailAndStatus(eventDTO.activity_title(), user.getEmail(), StatusEnum.ACTIVE.getValue());
        if (eventAlreadyExists.isEmpty()){
            throw new EventAlreadyExistsException(eventDTO.activity_title() + ": evento com esse título não existente ou usuário não é o criador do evento.");
        }

        var updatedEvent = new Event(eventAlreadyExists.get().getId(), eventDTO, StatusEnum.ACTIVE, user.getEmail());

        eventRepository.save(updatedEvent);

        return eventDTO;
    }
}
