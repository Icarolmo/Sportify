package org.sportify.SportifyApplication.repository;

import org.sportify.SportifyApplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);
}
