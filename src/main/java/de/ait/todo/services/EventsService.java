package de.ait.todo.services;

import de.ait.todo.dto.EventDTO;
import de.ait.todo.dto.NewEventDTO;

public interface EventsService {

    Long addEvent(Long currentUserID, NewEventDTO event);

}
