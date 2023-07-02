package de.ait.todo.repositories;

import de.ait.todo.models.Event;
import de.ait.todo.models.Task;
import de.ait.todo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository <Event, Long> {
    List<Event> findAllByOwner_Id(Long userId);
  //  List<Event> findByUser(Long userId);
    List<Event> findAllByOwnerAndIsBlockedAndOwner_IsBlocked(User owner, Boolean isBlocked, Boolean isBlockedOwner);
    List<Event> findByIdAndIsBlocked(Long userId, Boolean isBlockedEvent);

}
