package org.sportify.SportifyApplication.repository;

import org.sportify.SportifyApplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

    User findByLogin(String login);
}
