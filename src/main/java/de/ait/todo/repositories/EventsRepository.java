package de.ait.todo.repositories;

import de.ait.todo.dto.EventsPage;
import de.ait.todo.models.Event;
import de.ait.todo.models.Task;
import de.ait.todo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends JpaRepository <Event, Long> {
    List<Event> findAllByOwner_Id(Long userId);

    List<Event> findAllByPlace(String place);

    List<Event> findAllByCategory(String category);

    List<Event> findByMembersContains(User eventMember);
    List<Event> findAllByIsBlockedFalse();
    Optional<Event> findByIdAndIsBlockedFalse(Long eventId);

  //  List<Event> findByUser(Long userId);
    List<Event> findAllByOwnerAndIsBlockedAndOwner_IsBlocked(User owner, Boolean isBlocked, Boolean isBlockedOwner);
    List<Event> findByIdAndIsBlocked(Long userId, Boolean isBlockedEvent);

    @Query(value = "select distinct event.category from Event event")
    List<String> getCategories();

    @Query(value = "select distinct event.place from Event event")
    List<String> getPlaces();

}
