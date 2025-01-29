package org.stadium.stadium_management.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Ticket_Id;


    private String Ticket_Type;
    private String Ticket_Status;
    private long Ticket_Cost;
    private LocalDateTime Ticket_Start;
    private LocalDateTime Ticket_End;

}
