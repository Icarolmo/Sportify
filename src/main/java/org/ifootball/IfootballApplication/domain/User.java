package org.ifootball.IfootballApplication.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Table(name = "users")
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private int role;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "second_name")
    private String second_name;

    @Column(name = "email")
    private String email;

    @Column(name = "course_name")
    private int course_name;

    @Column(name = "current_semester")
    private int current_semester;
}
