package de.ait.todo.repositories;

import de.ait.todo.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepository extends CrudRepository <Event, Integer> {

}
