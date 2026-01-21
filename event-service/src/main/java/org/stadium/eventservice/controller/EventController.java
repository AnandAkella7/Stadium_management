package org.stadium.eventservice.controller;

import org.stadium.eventservice.dto.EventDto;
import org.stadium.eventservice.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/search")
    public List<EventDto> searchEvents(@RequestParam String q) {
        return eventService.searchEvents(q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto dto) {
        return ResponseEntity.ok(eventService.createEvent(dto));
    }
}
