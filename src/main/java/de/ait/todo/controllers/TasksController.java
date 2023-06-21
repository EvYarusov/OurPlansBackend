package de.ait.todo.controllers;

import de.ait.todo.controllers.api.TasksApi;
import de.ait.todo.dto.TaskDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.TasksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class TasksController implements TasksApi {

    private final TasksService tasksService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<TasksPage> getAll() {
        return ResponseEntity
                .ok(tasksService.getAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<TaskDto> getById(Long taskId) {
        return ResponseEntity.ok(tasksService.getById(taskId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void deleteTask(Long taskId) {
        tasksService.deleteTask(taskId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<TaskDto> addTask(AuthenticatedUser authenticatedUser, TaskDto task) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity.status(201)
                .body(tasksService.addTask(currentUserId, task));
    }
}
