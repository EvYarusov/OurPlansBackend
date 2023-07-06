package de.ait.todo.services;

import de.ait.todo.dto.*;
import de.ait.todo.security.details.AuthenticatedUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventsService {

    EventDTO addEvent(Long currentUserID, NewEventDTO newEventDTO);

    EventsPage getAllEvents ();

    EventDTO getEventById(Long eventId);

    EventsPage getEventsByOwnerId(Long userId);

    EventDTO eventBlock(Long eventId, Boolean isBlock);

    List<UserDto> getMembersByEventId(Long eventId);

    UsersPage takePartInEvent(AuthenticatedUser authenticatedUser, Long eventId);

    UsersPage eventOut(AuthenticatedUser authenticatedUser, Long eventId);

    EventsPage getEventsByPlace(String place);

    EventsPage getEventsByCategory(String category);

    EventsPage getEventsCreatedByMe(AuthenticatedUser authenticatedUser);

    EventsPage getEventsWereIAmMember(AuthenticatedUser authenticatedUser);

    EventDTO updateEvent(AuthenticatedUser authenticatedUser, Long eventId, NewEventDTO newEventDTO);

    StringPage getAllCategory();

    StringPage getAllPlaces();

    void deleteEvent(AuthenticatedUser authenticatedUser, Long eventId);

    EventsPage allEventsOfUserBlock(Long userId, Boolean isBlock);
}
