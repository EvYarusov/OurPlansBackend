package de.ait.todo.repositories;

import de.ait.todo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser_Id(Long userId);
}
