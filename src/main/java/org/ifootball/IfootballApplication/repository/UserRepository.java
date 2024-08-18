package org.ifootball.IfootballApplication.repository;

import org.ifootball.IfootballApplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
