package de.ait.todo.services;

import de.ait.todo.dto.*;
import de.ait.todo.security.details.AuthenticatedUser;

import java.util.List;

public interface EventsService {

    EventDTO addEvent(Long currentUserID, NewEventDTO newEventDTO);

    EventsPage getAllEvents ();

    EventDTO getEventById(Long eventId);

    EventsPage getEventsByUserId(Long userId);

    EventDTO eventBlock(Long eventId, Boolean isBlock);

    List<UserDto> getMembersByEventId(Long eventId);

    Integer takePartInEvent(AuthenticatedUser authenticatedUser, Long eventId);

    Integer eventOut(AuthenticatedUser authenticatedUser, Long eventId);

}
