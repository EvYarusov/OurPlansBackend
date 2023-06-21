package de.ait.todo.services.impl;

import de.ait.todo.dto.TaskDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.exceptions.NotFoundException;
import de.ait.todo.models.Task;
import de.ait.todo.models.User;
import de.ait.todo.repositories.TasksRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static de.ait.todo.dto.TaskDto.from;


@RequiredArgsConstructor
@Service
public class TasksServiceImpl implements TasksService {

    private final TasksRepository tasksRepository;

    private final UsersRepository usersRepository;

    @Override
    public TasksPage getAll() {
        return TasksPage.builder()
                .tasks(from(tasksRepository.findAll()))
                .build();
    }

    @Override
    public TaskDto getById(Long taskId) {
        Task task = tasksRepository.findById(taskId).orElseThrow(
                () -> new NotFoundException("Задача <" + taskId + "> не найдена"));

        return from(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (tasksRepository.existsById(taskId)) {
            tasksRepository.deleteById(taskId);
        } else {
            throw new NotFoundException("Задача <" + taskId + "> не найдена");
        }
    }

    @Override
    public TaskDto addTask(Long currentUserId, TaskDto task) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);

        Task newTask = Task.builder()
                .name(task.getName())
                .description(task.getDescription())
                .user(user)
                .build();

        tasksRepository.save(newTask);

        return from(newTask);
    }
}
