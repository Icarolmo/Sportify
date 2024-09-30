package org.sportify.SportifyApplication.repository;

import org.sportify.SportifyApplication.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    Optional<Event> findByActivityTitle(String activityTitle);

    Optional<List<Event>> findByType(String type);
}
