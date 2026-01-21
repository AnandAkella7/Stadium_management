package org.stadium.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private String category;
    private LocalDateTime date;
    private String location;
    private BigDecimal price;
    private Integer availableSeats;
    private String status;
}
