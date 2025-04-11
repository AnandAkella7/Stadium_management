package org.stadium.eventservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.stadium.eventservice.entity.Event;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateEventDto {
    
    @NotBlank(message = "Event name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;
    
    private UUID venueId;
    
    private String venueName;
    
    @Min(value = 1, message = "Total seats must be at least 1")
    private Integer totalSeats;
    
    @Min(value = 0, message = "Ticket price cannot be negative")
    private Double ticketPrice;
    
    private Event.EventStatus status = Event.EventStatus.DRAFT;
}
