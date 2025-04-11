package org.stadium.eventservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stadium.eventservice.dto.EventResponseDto;
import org.stadium.eventservice.service.EventService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/internal/events")
@RequiredArgsConstructor
public class InternalEventController {
    
    private final EventService eventService;
    
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
    
    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<EventResponseDto>> getEventsByVenue(@PathVariable UUID venueId) {
        return ResponseEntity.ok(eventService.getEventsByVenue(venueId));
    }
}