package org.ifootball.IfootballApplication.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifootball.IfootballApplication.dto.EventDTO;

@Entity(name = "event")
@Table(name = "events", schema = "ifootball")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "time")
    private String time;

    @Column(name = "number_of_person")
    private Integer number_of_person;

    @Column(name = "localization")
    private String localization;

    public Event(EventDTO eventDTO) {
        this.name = eventDTO.getName();
        this.type = eventDTO.getType();
        this.time = eventDTO.getTime();
        this.number_of_person = eventDTO.getNumber_of_person();
        this.localization = eventDTO.getLocalization();
    }

}
