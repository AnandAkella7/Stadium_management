package org.stadium.eventservice;

import org.stadium.eventservice.entity.Event;
import org.stadium.eventservice.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final EventRepository eventRepository;

    public DataInitializer(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (eventRepository.count() == 0) {
            eventRepository.save(new Event(null, "Taylor Swift Eras Tour", "Concert", LocalDateTime.now().plusDays(30),
                    "Wembley Stadium", new BigDecimal("150.00"), 50000, 120, "AVAILABLE"));
            eventRepository.save(new Event(null, "Manchester United vs Liverpool", "Sports",
                    LocalDateTime.now().plusDays(7), "Old Trafford", new BigDecimal("80.00"), 75000, 500, "AVAILABLE"));
            eventRepository.save(new Event(null, "TechCrunch Disrupt", "Conference", LocalDateTime.now().plusDays(60),
                    "Moscone Center", new BigDecimal("299.00"), 5000, 5000, "AVAILABLE"));

            System.out.println("--- SAMPLE EVENTS SEEDED ---");
        }
    }
}
