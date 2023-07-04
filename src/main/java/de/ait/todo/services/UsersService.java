package de.ait.todo.services;


import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.UserDto;
import de.ait.todo.dto.UsersPage;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    UsersPage getAllUsers();

    UserDto getUserById(Long userId, Long currentUserId);

    UserDto blockUserById(Long userId);

    UserDto unblockUserById(Long userId);
}
