package org.stadium.stadium_management.repository;

import java.util.PriorityQueue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stadium.stadium_management.model.Ticket;
import org.stadium.stadium_management.model.TicketStatus;

import java.util.List;


@Repository
public interface Ticket_repo extends JpaRepository<Ticket, Long>{

    PriorityQueue<Ticket> ticketQueue= new PriorityQueue<>(); //priority queue for cheapest tickets

    List<Ticket> findByTicket_Status(TicketStatus ticket_Status);

    default Ticket getCheapestTicket()
    {
        return ticketQueue.peek();
    }

    default void addTicket(Ticket ticket)
    {
        if(ticket.getTicket_Status() == TicketStatus.AVAILABLE)
        {
            ticketQueue.offer(ticket);
        }
    }


}
