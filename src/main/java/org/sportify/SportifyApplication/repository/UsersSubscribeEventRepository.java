package org.sportify.SportifyApplication.repository;

import org.sportify.SportifyApplication.domain.UsersSubscribeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersSubscribeEventRepository extends JpaRepository<UsersSubscribeEvent, Integer> {

    Optional<UsersSubscribeEvent> findByActivityTitleAndUserEmail(String activityTitle, String userEmail);
}
