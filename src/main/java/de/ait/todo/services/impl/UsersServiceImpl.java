package de.ait.todo.services.impl;

import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.dto.UserDto;
import de.ait.todo.dto.UsersPage;
import de.ait.todo.exceptions.NotFoundException;
import de.ait.todo.models.Task;
import de.ait.todo.models.User;
import de.ait.todo.repositories.TasksRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.ait.todo.dto.TaskDto.from;
import static de.ait.todo.models.User.Role.ADMIN;
import static de.ait.todo.models.User.Role.USER;


@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final TasksRepository tasksRepository;

    @Override
    public ProfileDto getProfile(Long currentUserId) {

        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);

        return ProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public TasksPage getTasksByUser(Long currentUserId) {

        List<Task> tasks = tasksRepository.findAllByUser_Id(currentUserId);

        return TasksPage.builder()
                .tasks(from(tasks))
                .build();

    }

    @Override
    public UsersPage getAllUsers() {

        List<User> users = usersRepository.findAll();

        return UsersPage.builder()
                .users(UserDto.from(users))
                .build();
    }

    @Override
    public UserDto getUserById(Long userId, Long currentUserId) {

        User user = usersRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Пользователь с id <" + userId + "> не найден")
                );

        User currentUser = usersRepository.findById(currentUserId)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Пользователь с id <" + userId + "> не найден")
                );

        if (currentUser.getRole().equals(USER)) {
            if (user.isBlocked()) {
                throw new NotFoundException("Пользователь с id <" + userId + "> не найден");
            }
        }

        return UserDto.from(user);
    }

    @Override
    public UserDto blockUserById(Long userId) {

        User user = usersRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Пользователь с id <" + userId + "> не найден")
                );
        //TODO написать Exception("Нельзя заблокировать администратора");
        if (user.getRole().equals(ADMIN)) {
            //throw new Exception("Нельзя заблокировать администратора");
        } else {
            user.setBlocked(true);
            usersRepository.save(user);
        }

        return UserDto.from(user);
    }

    @Override
    public UserDto unblockUserById(Long userId) {

        User user = usersRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException(
                                "Пользователь с id <" + userId + "> не найден")
                );

        user.setBlocked(false);

        usersRepository.save(user);

        return UserDto.from(user);
    }

}
