package org.ifootball.IfootballApplication.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ifootball.IfootballApplication.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String login;
    private int role;
    private String first_name;
    private String second_name;
    private String email;
    private int course_name;
    private int current_semester;

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.role = user.getRole();
        this.first_name = user.getFirst_name();
        this.second_name = user.getSecond_name();
        this.email = user.getEmail();
        this.course_name = user.getCourse_name();
        this.current_semester = user.getCurrent_semester();
    }
}
