package org.stadium.eventservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.stadium.eventservice.entity.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    
    Page<Event> findAllByOrderByStartTimeDesc(Pageable pageable);
    
    Page<Event> findByCreatedByOrderByStartTimeDesc(String createdBy, Pageable pageable);
    
    @Query("SELECT e FROM Event e WHERE e.startTime >= :now AND e.status = 'PUBLISHED' ORDER BY e.startTime ASC")
    Page<Event> findUpcomingEvents(LocalDateTime now, Pageable pageable);
    
    @Query("SELECT e FROM Event e WHERE e.startTime BETWEEN :start AND :end AND e.status = 'PUBLISHED'")
    List<Event> findEventsInDateRange(LocalDateTime start, LocalDateTime end);
    
    List<Event> findByVenueId(UUID venueId);
    
    @Query("SELECT COUNT(e) FROM Event e WHERE e.status = 'PUBLISHED'")
    long countPublishedEvents();
}