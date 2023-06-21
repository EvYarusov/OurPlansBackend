package de.ait.todo.controllers;

import de.ait.todo.controllers.api.UsersApi;
import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UsersService usersService;


    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<ProfileDto> getProfile(AuthenticatedUser currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        ProfileDto profile = usersService.getProfile(currentUserId);

        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<TasksPage> getMyTasks(AuthenticatedUser currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        return ResponseEntity.ok(usersService.getTasksByUser(currentUserId));
    }
}
