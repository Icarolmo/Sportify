package org.ifootball.IfootballApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifootball.IfootballApplication.domain.Event;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO {

    private String name;

    private String type;

    private String time_event;

    private Integer number_of_person;

    private String localization;

    public EventDTO(Event event) {
        this.name = event.getName();
        this.type = event.getType();
        this.time_event = event.getTime_event();
        this.number_of_person = event.getNumber_of_person();
        this.localization = event.getLocalization();
    }

    public static List<EventDTO> EventListForEventDTOList(List<Event> eventList) {
        List<EventDTO> eventDTOList = new ArrayList<EventDTO>();

        eventList.stream()
                .forEach( event -> eventDTOList.add(new EventDTO(event)));

        return eventDTOList;
    }
}
