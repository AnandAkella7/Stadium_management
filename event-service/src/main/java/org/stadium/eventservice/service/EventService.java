package org.stadium.eventservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.stadium.eventservice.dto.CreateEventDto;
import org.stadium.eventservice.dto.EventResponseDto;
import org.stadium.eventservice.dto.UpdateEventDto;
import org.stadium.eventservice.entity.Event;
import org.stadium.eventservice.exception.ResourceNotFoundException;
import org.stadium.eventservice.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    
    private final EventRepository eventRepository;
    
    public Page<EventResponseDto> getAllEvents(Pageable pageable) {
        return eventRepository.findAllByOrderByStartTimeDesc(pageable)
                .map(EventResponseDto::fromEntity);
    }
    
    public Page<EventResponseDto> getEventsByCreator(String email, Pageable pageable) {
        return eventRepository.findByCreatedByOrderByStartTimeDesc(email, pageable)
                .map(EventResponseDto::fromEntity);
    }
    
    public Page<EventResponseDto> getUpcomingEvents(Pageable pageable) {
         try {
            // Remove any problematic sort properties
            pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()
            );
            
            return eventRepository.findUpcomingEvents(LocalDateTime.now(), pageable)
                    .map(EventResponseDto::fromEntity);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error fetching upcoming events: " + e.getMessage());
            // Return an empty page instead of letting the exception propagate
            return Page.empty();
        }
    }
    
    public EventResponseDto getEventById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        return EventResponseDto.fromEntity(event);
    }
    
    @Transactional
    public EventResponseDto createEvent(CreateEventDto createEventDto, String creatorEmail) {
        Event event = Event.builder()
                .name(createEventDto.getName())
                .description(createEventDto.getDescription())
                .startTime(createEventDto.getStartTime())
                .endTime(createEventDto.getEndTime())
                .venueId(createEventDto.getVenueId())
                .venueName(createEventDto.getVenueName())
                .totalSeats(createEventDto.getTotalSeats())
                .availableSeats(createEventDto.getTotalSeats()) // Initially, all seats are available
                .ticketPrice(createEventDto.getTicketPrice())
                .status(createEventDto.getStatus())
                .createdBy(creatorEmail)
                .build();
        
        Event savedEvent = eventRepository.save(event);
        return EventResponseDto.fromEntity(savedEvent);
    }
    
    @Transactional
    public EventResponseDto updateEvent(UUID id, UpdateEventDto updateEventDto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        
        // Update only non-null fields
        if (updateEventDto.getName() != null) {
            event.setName(updateEventDto.getName());
        }
        
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        
        if (updateEventDto.getStartTime() != null) {
            event.setStartTime(updateEventDto.getStartTime());
        }
        
        if (updateEventDto.getEndTime() != null) {
            event.setEndTime(updateEventDto.getEndTime());
        }
        
        if (updateEventDto.getVenueId() != null) {
            event.setVenueId(updateEventDto.getVenueId());
        }
        
        if (updateEventDto.getVenueName() != null) {
            event.setVenueName(updateEventDto.getVenueName());
        }
        
        if (updateEventDto.getTotalSeats() != null) {
            // Calculate the difference to adjust available seats
            int seatsDifference = updateEventDto.getTotalSeats() - event.getTotalSeats();
            event.setTotalSeats(updateEventDto.getTotalSeats());
            event.setAvailableSeats(event.getAvailableSeats() + seatsDifference);
        }
        
        if (updateEventDto.getTicketPrice() != null) {
            event.setTicketPrice(updateEventDto.getTicketPrice());
        }
        
        if (updateEventDto.getStatus() != null) {
            event.setStatus(updateEventDto.getStatus());
        }
        
        Event updatedEvent = eventRepository.save(event);
        return EventResponseDto.fromEntity(updatedEvent);
    }
    
    @Transactional
    public void deleteEvent(UUID id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event", "id", id);
        }
        eventRepository.deleteById(id);
    }
    
    @Transactional
    public EventResponseDto publishEvent(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        
        event.setStatus(Event.EventStatus.PUBLISHED);
        Event publishedEvent = eventRepository.save(event);
        
        return EventResponseDto.fromEntity(publishedEvent);
    }
    
    @Transactional
    public EventResponseDto cancelEvent(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
        
        event.setStatus(Event.EventStatus.CANCELLED);
        Event cancelledEvent = eventRepository.save(event);
        
        return EventResponseDto.fromEntity(cancelledEvent);
    }
    
    public List<EventResponseDto> getEventsByVenue(UUID venueId) {
        return eventRepository.findByVenueId(venueId)
                .stream()
                .map(EventResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}