package org.sportify.SportifyApplication.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.sportify.SportifyApplication.dto.EventDTO;
import org.sportify.SportifyApplication.enums.StatusEnum;
import org.sportify.SportifyApplication.exception.EventBodyWithIncorrectDataException;

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
        if(Strings.isBlank(activityTitle)){
            throw new EventBodyWithIncorrectDataException("activity_title cannot be blank");
        }
        if(activityTitle.length() > 20 || activityTitle.length() < 3) {
            throw new EventBodyWithIncorrectDataException("activity_title must be between 3 and 20 characters");
        }
    }

    public void ValidateEvent(){
        if (this.activityTitle.isEmpty()) {
            throw new EventBodyWithIncorrectDataException("nome do evento vazio ou nulo.");
        }
        if (this.date == null) {
            throw new EventBodyWithIncorrectDataException("horário do evento vazio ou nulo.");
        }
        if (LocalDateTime.of(this.date, this.startHour).isBefore(LocalDateTime.now())) {
            throw new EventBodyWithIncorrectDataException("o horário/data do evento a ser criado já passou.");
        }
        if (LocalTime.of(this.endHour.getHour(), this.endHour.getMinute(), this.endHour.getSecond())
                .isBefore(this.startHour)){
            throw new EventBodyWithIncorrectDataException("o horário de encerramento do evento é anterior ao de inicío.");
        }
        if (this.localization.isEmpty()) {
            throw new EventBodyWithIncorrectDataException("localização do evento vazia ou nula.");
        }
    }
}
