package de.ait.todo.services;


import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    TasksPage getTasksByUser(Long currentUserId);
}
