package org.sportify.SportifyApplication.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sportify.SportifyApplication.dto.EventDTO;
import org.sportify.SportifyApplication.enums.StatusEnum;
import org.sportify.SportifyApplication.exception.RequestBodyWithIncorrectDataException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Entity(name = "event")
@Table(name = "events", schema = "sportify")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "activity_title")
    private String activityTitle;

    @Column(name = "type")
    private String type;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "localization")
    private String localization;
    
    @Column(name = "date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Column(name = "start_hour")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startHour;
    
    @Column(name = "end_hour")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endHour;
    
    @Column(name = "privacy")
    private String privacy;
    
    @Column(name = "number_of_person")
    private Integer numberOfPerson;

    @Column(name = "status")
    private int status;

    public Event(EventDTO eventDTO) {
        this.activityTitle = eventDTO.activity_title();
        this.type = eventDTO.type();
        this.description = eventDTO.description();
        this.localization = eventDTO.localization();
        this.date = eventDTO.date();
        this.startHour = eventDTO.start_hour();
        this.endHour = eventDTO.end_hour();
        this.privacy = eventDTO.privacy();
        this.numberOfPerson = eventDTO.number_of_person();
    }

    public Event(EventDTO eventDTO, StatusEnum status) {
        this.activityTitle = eventDTO.activity_title();
        this.type = eventDTO.type();
        this.description = eventDTO.description();
        this.localization = eventDTO.localization();
        this.date = eventDTO.date();
        this.startHour = eventDTO.start_hour();
        this.endHour = eventDTO.end_hour();
        this.privacy = eventDTO.privacy();
        this.numberOfPerson = eventDTO.number_of_person();
        this.status = status.getValue();
    }

    public static List<Event> EventDTOListForEventEntity(List<EventDTO> eventDTOList) {
        List<Event> eventEntityList = new ArrayList<Event>();
        eventDTOList.stream()
                .forEach( event -> eventEntityList.add(new Event(event)));

        return eventEntityList;
    }

    public static List<EventDTO> EventEntityListForEventDTO(List<Event> eventList) {
        List<EventDTO> eventDTOList = new ArrayList<EventDTO>();
        eventList.stream()
                .forEach( event -> eventDTOList.add(
                        new EventDTO(event.getActivityTitle(), event.getType(), event.getDescription(), event.getLocalization(),
                                event.getDate(), event.getStartHour(), event.getEndHour(), event.getPrivacy(), event.getNumberOfPerson()))
                );

        return eventDTOList;
    }

    public static void ValidateActivityTitle(String activityTitle){
        if(activityTitle.isBlank()){
            throw new RequestBodyWithIncorrectDataException("activity_title: titulo do evento não pode ser nulo ou vazio");
        }
        if(activityTitle.length() > 20 || activityTitle.length() < 3) {
            throw new RequestBodyWithIncorrectDataException("activity_title: titulo do evento tem que ser maior que 3 e menor que 20 characters");
        }
    }

    public static void IsEmpty(EventDTO eventDTO){
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        if (eventDTO == null) {
            throw new RequestBodyWithIncorrectDataException("corpo da requisição nulo ou vazio.");
        }
        if (eventDTO.activity_title() == null) {
            errorMessage.append("activity_title: ").append("titulo do evento vazio ou nulo. ");
            isValid = false;
        }
        if (eventDTO.type() == null) {
            errorMessage.append("type: ").append("tipo do evento vazio ou nulo. ");
            isValid = false;
        }
        if (eventDTO.localization() == null || eventDTO.localization().isEmpty()) {
            errorMessage.append("localization: ").append("localizao do evento vazio ou nulo. ");
            isValid = false;
        }
        if (eventDTO.date() == null) {
            errorMessage.append("date: ").append("data do evento vazio ou nulo. ");
            isValid = false;
        }
        if (eventDTO.start_hour() == null) {
            errorMessage.append("start_hour: ").append("horário de inicio do evento vazio ou nulo. ");
            isValid = false;
        }
        if (eventDTO.end_hour() == null) {
            errorMessage.append("end_hour: ").append("horário de termino do evento vazio ou nulo. ");
            isValid = false;
        }
        if (eventDTO.privacy() == null || eventDTO.privacy().isEmpty()) {
            errorMessage.append("privacy: ").append("privacidade do evento vazio ou nulo. ");
            isValid = false;
        }
        if (eventDTO.number_of_person() <= 0) {
            errorMessage.append("number_of_person: ").append("numero de pessoas participantes do evento vazio ou nulo. ");
            isValid = false;
        }

        if(!isValid){
            throw new RequestBodyWithIncorrectDataException(errorMessage.toString());
        }
    }

    public static void ValidateEvent(EventDTO eventDTO){
        boolean eventIsValid = true;
        StringBuilder errorMessage = new StringBuilder();
        if(eventDTO == null){
            throw new RequestBodyWithIncorrectDataException("corpo/body da requisição é nulo ou vazio.");
        }
        if(eventDTO.activity_title().isEmpty() || eventDTO.activity_title().length() < 3 || eventDTO.activity_title().length() > 20) {
            errorMessage.append("activity_title: ").append("nome do evento vazio, nulo ou tamanho inválido menor que 3 caracteres ou maior que 20 caracteres.");
            eventIsValid = false;
        }
        if(eventDTO.localization().isEmpty()) {
            errorMessage.append("localization: ").append("localização do evento vazia.");
            eventIsValid = false;
        }
        if(eventDTO.type().isEmpty()) {
            errorMessage.append("type: ").append("tipo do evento vazio.");
            eventIsValid = false;
        }
        if(eventDTO.date() == null) {
            errorMessage.append("date: ").append("horário do evento vazio ou nulo.");
            eventIsValid = false;
        }
        if(LocalDateTime.of(eventDTO.date(), eventDTO.start_hour()).isBefore(LocalDateTime.now())) {
            errorMessage.append("date/start_hour: ").append("o horário/data do evento a ser criado já passou.");
        }
        if(LocalTime.of(eventDTO.end_hour().getHour(), eventDTO.end_hour().getMinute(), eventDTO.end_hour().getSecond())
                .isBefore(eventDTO.start_hour())){
            errorMessage.append("start_hour/end_hour: ").append("o horário de encerramento do evento é anterior ao de inicío.");
            eventIsValid = false;
        }
        if(eventDTO.privacy().isEmpty()){
            errorMessage.append("privacy: ").append("privacidade do evento vazia.");
            eventIsValid = false;
        }

        if(!eventIsValid) {
            throw new RequestBodyWithIncorrectDataException(errorMessage.toString());
        }
    }

    public static String PrintEventDTOData(EventDTO eventDTO){
        return "activity_title: " + eventDTO.activity_title() +
                "type: " + eventDTO.activity_title() +
                "description: " + eventDTO.activity_title() +
                "localization: " + eventDTO.activity_title() +
                "date: " + eventDTO.activity_title() +
                "start_hour: " + eventDTO.activity_title() +
                "end_hour: " + eventDTO.activity_title() +
                "privacy: " + eventDTO.activity_title() +
                "number_of_person: " + eventDTO.number_of_person();
    }

    public String PrintEventData(){
        return "activity_title: " + this.getActivityTitle() +
                " type: " + this.getType() +
                " description: " + this.getDescription() +
                " localization: " + this.getLocalization() +
                " date: " + this.getDate() +
                " start_hour: " + this.getStartHour() +
                " end_hour: " + this.getEndHour() +
                " privacy: " + this.getPrivacy() +
                " number_of_person: " + this.getNumberOfPerson() +
                " status: " + this.getStatus();
    }

}
