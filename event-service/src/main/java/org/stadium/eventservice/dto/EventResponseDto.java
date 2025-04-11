package org.stadium.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stadium.eventservice.entity.Event;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDto {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UUID venueId;
    private String venueName;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double ticketPrice;
    private Event.EventStatus status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EventResponseDto fromEntity(Event event) {
        return EventResponseDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .venueId(event.getVenueId())
                .venueName(event.getVenueName())
                .totalSeats(event.getTotalSeats())
                .availableSeats(event.getAvailableSeats())
                .ticketPrice(event.getTicketPrice())
                .status(event.getStatus())
                .createdBy(event.getCreatedBy())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }
}
