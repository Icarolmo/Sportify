package org.sportify.SportifyApplication.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "users_subscribe_event")
@Entity(name = "user_subscribe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "user_mail")
public class UsersSubscribeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "activity_title")
    private String activityTitle;

    public UsersSubscribeEvent(String activityTitle, String userEmail){
        this.userEmail = userEmail;
        this.activityTitle = activityTitle;
    }
}
