package org.stadium.eventservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // "Concert", "Sports", "Conference"
    private String category;

    private LocalDateTime date;

    private String location;

    private BigDecimal price;

    private Integer totalSeats;

    private Integer availableSeats;

    // "AVAILABLE", "SOLD_OUT", "CANCELLED"
    private String status;
}
