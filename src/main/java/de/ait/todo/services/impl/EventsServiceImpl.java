package de.ait.todo.services.impl;

import de.ait.todo.dto.EventDTO;
import de.ait.todo.dto.NewEventDTO;
import de.ait.todo.models.Event;
import de.ait.todo.models.User;
import de.ait.todo.repositories.EventsRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.EventsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventsServiceImpl implements EventsService {

    private final static ModelMapper modelMapper = new ModelMapper();
    private final EventsRepository eventsRepository;

    private final UsersRepository usersRepository;

    @Override
    public Long addEvent(Long currentUserID, NewEventDTO newEventDTO) {

        Event event = modelMapper.map(newEventDTO, Event.class);
        event.setAuthorId(currentUserID);

        return eventsRepository.save(event).getId();
    }
}
