package de.ait.todo.controllers;


import de.ait.todo.controllers.api.EventApi;
import de.ait.todo.dto.*;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class EventController implements EventApi {

    private final EventsService eventsService;

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EventDTO> addEvent(AuthenticatedUser authenticatedUser, NewEventDTO newEventDTO) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity.status(201)
                .body(eventsService.addEvent(currentUserId, newEventDTO));
    }

    @PermitAll
    @Override
    public ResponseEntity <EventsPage> getAllEvents() {
        return ResponseEntity
                .ok(eventsService.getAllEvents());
    }

    @PermitAll
    @Override
    public ResponseEntity<EventDTO> getEventById(Long eventId) {
        return ResponseEntity.ok(eventsService.getEventById(eventId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity<EventsPage> getEventsByOwnerId(Long userId) {
        return ResponseEntity
                .ok(eventsService.getEventsByOwnerId(userId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<EventDTO> eventBlock(Long eventId, Boolean isBlock) {
        return ResponseEntity
                .ok(eventsService.eventBlock(eventId, isBlock));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity<List<UserDto>> getMembersByEventId(Long eventId) {
        return ResponseEntity
                .ok(eventsService.getMembersByEventId(eventId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<UsersPage> takePartInEvent(AuthenticatedUser authenticatedUser, Long eventId) {
        return ResponseEntity
                .ok(eventsService.takePartInEvent(authenticatedUser, eventId));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<UsersPage> eventOut(AuthenticatedUser authenticatedUser, Long eventId) {
        return ResponseEntity
                .ok(eventsService.eventOut(authenticatedUser, eventId));
    }

    @PermitAll
    @Override
    public ResponseEntity<EventsPage> getEventsByPlace(String place) {
        return ResponseEntity
                .ok(eventsService.getEventsByPlace(place));
    }

    @Override
    public ResponseEntity<EventsPage> getEventsByCategory(String category) {
        return ResponseEntity
                .ok(eventsService.getEventsByCategory(category));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EventsPage> getEventsCreatedByMe(AuthenticatedUser authenticatedUser) {
        return ResponseEntity
                .ok(eventsService.getEventsCreatedByMe(authenticatedUser));
    }
    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<EventsPage> getEventsWereIAmMember(AuthenticatedUser authenticatedUser) {
        return ResponseEntity
                .ok(eventsService.getEventsWereIAmMember(authenticatedUser));
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity<EventDTO> updateEvent(AuthenticatedUser authenticatedUser, Long eventId, NewEventDTO newEventDTO) {
        return ResponseEntity
                .ok(eventsService.updateEvent(authenticatedUser, eventId, newEventDTO));
    }

    @Override
    public ResponseEntity<StringPage> getAllCategory() {
        return ResponseEntity
                .ok(eventsService.getAllCategory());
    }

    @Override
    public ResponseEntity<StringPage> getAllPlaces() {
        return ResponseEntity
                .ok(eventsService.getAllPlaces());
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public void deleteEvent(AuthenticatedUser authenticatedUser, Long eventId) {
        eventsService.deleteEvent(authenticatedUser, eventId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<EventsPage> allEventsOfUserBlock(Long userId, Boolean isBlock) {
        return ResponseEntity
                .ok(eventsService.allEventsOfUserBlock(userId, isBlock));

    }


}
