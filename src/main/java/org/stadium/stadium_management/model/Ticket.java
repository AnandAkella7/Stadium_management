package org.stadium.stadium_management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketType ticketType;

    @Column(nullable = false)
    private TicketStatus ticketStatus;

    @Column(nullable = false)
    private long ticketCost;

    @Column(nullable = false)
    private LocalDateTime ticketStart;
    @Column(nullable = false)
    private LocalDateTime ticketEnd;

    public long getTicketCost()
    {
        return ticketCost;
    }


@ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

}
