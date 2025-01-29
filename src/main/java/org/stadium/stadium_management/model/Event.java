package org.stadium.stadium_management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;

    private String name;
    private String location;
    private String description;
    private LocalDateTime datetime;



}
