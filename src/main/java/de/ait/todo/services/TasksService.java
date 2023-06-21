package de.ait.todo.services;

import de.ait.todo.dto.TaskDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.security.details.AuthenticatedUser;


public interface TasksService {
    TasksPage getAll();

    TaskDto getById(Long taskId);

    void deleteTask(Long taskId);

    TaskDto addTask(Long currentUserId, TaskDto task);
}
