package org.stadium.stadium_management.service;

import java.util.PriorityQueue;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.stadium.stadium_management.model.Ticket;
import org.stadium.stadium_management.model.Event;
import org.stadium.stadium_management.model.TicketStatus;
import org.stadium.stadium_management.repository.TicketRepository;
import java.util.concurrent.locks.ReentrantLock;

import com.ctc.wstx.shaded.msv_core.datatype.xsd.Comparator;

@Service
public class TicketService {


    @Autowired
    private TicketRepository ticketRepository;


    private final PriorityQueue<Ticket> ticketQueue = new PriorityQueue<>(Comparator.comparingLong(Ticket::getTicketCost));
    private final ReentrantLock lock = new ReentrantLock();

    public void addTicketToQueue(Ticket ticket) {
        ticketRepository.save(ticket); // Add ticket to database
        if(ticket.getTicketStatus() == TicketStatus.AVAILABLE){
            ticketQueue.offer(ticket);
        }
    }


    public Ticket getCheapestAvailableTicket(long eventId){
        Event event = new Event();
        event.setId(eventId);
        return ticketQueue.peek();
    }

    public Ticket saveTicket(Ticket ticket)
    {
        Ticket savedTicket = ticketRepository.save(ticket);
        addTicketToQueue(savedTicket);
        return savedTicket;
    }

    public synchronized Ticket bookCheapestTicket()
    {
        lock.lock();
        try
        {
            while(!ticketQueue.isEmpty())
            {
                Ticket cheapestTicket = ticketQueue.peek();
                if(cheapestTicket != null)
                {
                cheapestTicket.setTicketStatus(TicketStatus.CONFIRMED);
                ticketQueue.poll(); //Remove tickets from the queue
                return ticketRepository.save(cheapestTicket); //update the database
                }
                return null;
        }
    } 
    
    public void deleteTicket(long id) {
        ticketRepository.deleteById(id); // Delete ticket from database
        ticketQueue.removeIf(t -> t.getTicketId() == id); // Remove ticket from queue if present
    }






}
}
