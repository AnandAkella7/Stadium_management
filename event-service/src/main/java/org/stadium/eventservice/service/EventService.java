package org.stadium.eventservice.service;

import org.stadium.eventservice.dto.EventDto;
import java.util.List;

public interface EventService {
    EventDto createEvent(EventDto eventDto);

    EventDto getEventById(Long id);

    List<EventDto> getAllEvents();

    List<EventDto> searchEvents(String query);
}
