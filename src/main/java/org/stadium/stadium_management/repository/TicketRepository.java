package org.stadium.stadium_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 
import org.stadium.stadium_management.model.Ticket;
import org.stadium.stadium_management.model.Event;
import org.stadium.stadium_management.model.TicketStatus;

import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{


    // Find all tickets by status
    List<Ticket> findByTicketStatus(TicketStatus ticket_Status);

    // Find tickets by status and event.
     List<Ticket> getTicketAndEvent(Event event, TicketStatus ticket_Status);


}
