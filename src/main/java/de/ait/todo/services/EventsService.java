package de.ait.todo.services;

import de.ait.todo.dto.EventDTO;
import de.ait.todo.dto.EventsPage;
import de.ait.todo.dto.NewEventDTO;

public interface EventsService {

    EventDTO addEvent(Long currentUserID, NewEventDTO newEventDTO);

    EventsPage getAllEvents ();

    EventDTO getEventById(Long eventId);

    EventsPage getEventsByUserId(Long userId);

    EventDTO eventBlock(Long eventId, Boolean isBlock);


}
