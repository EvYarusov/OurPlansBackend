package de.ait.todo.repositories;

import de.ait.todo.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository <Event, Long> {
    List<Event> findAllByUser_Id(Long userId);
    List<Event> findByUser(Long userId);

}
