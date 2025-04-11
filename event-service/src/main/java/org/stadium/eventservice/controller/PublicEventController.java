package org.stadium.eventservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stadium.eventservice.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public-events")
@RequiredArgsConstructor
public class PublicEventController {
    
    private final EventRepository eventRepository;
    
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getEvents() {
        List<Map<String, Object>> events = new ArrayList<>();
        // Just return an empty list for now to test the endpoint
        return ResponseEntity.ok(events);
    }
}
