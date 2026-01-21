package org.stadium.eventservice.service;

import org.stadium.eventservice.dto.EventDto;
import org.stadium.eventservice.entity.Event;
import org.stadium.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public EventDto createEvent(EventDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setCategory(dto.getCategory());
        event.setDate(dto.getDate());
        event.setLocation(dto.getLocation());
        event.setPrice(dto.getPrice());
        event.setTotalSeats(100); // Default for now
        event.setAvailableSeats(100);
        event.setStatus("AVAILABLE");

        Event saved = eventRepository.save(event);
        return mapToDto(saved);
    }

    @Override
    public EventDto getEventById(Long id) {
        return eventRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDto> searchEvents(String query) {
        return eventRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private EventDto mapToDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getCategory(),
                event.getDate(),
                event.getLocation(),
                event.getPrice(),
                event.getAvailableSeats(),
                event.getStatus());
    }
}
