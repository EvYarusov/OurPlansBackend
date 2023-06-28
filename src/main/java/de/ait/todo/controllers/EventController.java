package de.ait.todo.controllers;


import de.ait.todo.controllers.api.EventApi;
import de.ait.todo.dto.EventDTO;
import de.ait.todo.dto.EventsPage;
import de.ait.todo.dto.NewEventDTO;
import de.ait.todo.dto.TaskDto;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity <EventsPage> getAllEvents() {
        return ResponseEntity
                .ok(eventsService.getAllEvents());
    }

   // @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") //?
    @Override
    public ResponseEntity<EventDTO> getEventById(Long eventId) {
        return ResponseEntity.ok(eventsService.getEventById(eventId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity<EventsPage> getEventsByUserId(Long userId) {
        return ResponseEntity
                .ok(eventsService.getEventsByUserId(userId));
    }
}
