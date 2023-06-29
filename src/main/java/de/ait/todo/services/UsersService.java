package de.ait.todo.services;


import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.dto.UserDto;
import de.ait.todo.dto.UsersPage;
import de.ait.todo.security.details.AuthenticatedUser;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    TasksPage getTasksByUser(Long currentUserId);

    UsersPage getAllUsers();

    UserDto getUserById(Long userId, Long currentUserId);

    UserDto blockUserById(Long userId);

    UserDto unblockUserById(Long userId);
}
