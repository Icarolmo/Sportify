package org.sportify.SportifyApplication.repository;

import org.sportify.SportifyApplication.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    Optional<Event> findByActivityTitleAndCreatorMailAndStatus(String activityTitle, String creatorMail, int status);

    Optional<List<Event>> findByCreatorMail(String creatorMail);

    Optional<Event> findByActivityTitle(String activityTitle);

    @Query("SELECT e FROM event e WHERE e.activityTitle = :activityTitle AND e.status = 1 AND e.acceptSubscribes = 1")
    Optional<Event> findByActivityTitleAndActiveAndAcceptSubscribes(String activityTitle);

    Optional<List<Event>> findByType(String type);

    Optional<List<Event>> findByStatus(Integer status);

    //@Query("SELECT e FROM event e JOIN users_registered u ON e.activityTitle = u.activityTitle WHERE u.email = :email")
    //List<Event> findByEventRegistered(@Param("email") String email);
}
