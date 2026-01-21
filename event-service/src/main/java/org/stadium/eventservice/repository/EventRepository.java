package org.stadium.eventservice.repository;

import org.stadium.eventservice.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Finds events where title OR category matches the query (Case Insensitive)
    List<Event> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);
}
