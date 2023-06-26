package de.ait.todo.controllers;


import de.ait.todo.controllers.api.EventApi;
import de.ait.todo.dto.EventDTO;
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
    public ResponseEntity<Long> addEvent(AuthenticatedUser authenticatedUser, NewEventDTO newEventDTO) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity.status(201)
                .body(eventsService.addEvent(currentUserId, newEventDTO));
    }
}
