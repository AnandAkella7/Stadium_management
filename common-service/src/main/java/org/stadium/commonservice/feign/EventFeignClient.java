package org.stadium.commonservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "event-service",
        url = "${event-service.url:http://localhost:8082}",
        configuration = FeignSecurityConfiguration.class)
public interface EventFeignClient {
    
    @GetMapping("/api/internal/events/{id}")
    Object getEventById(@PathVariable UUID id);
    
    @GetMapping("/api/internal/events/venue/{venueId}")
    List<Object> getEventsByVenue(@PathVariable UUID venueId);
}