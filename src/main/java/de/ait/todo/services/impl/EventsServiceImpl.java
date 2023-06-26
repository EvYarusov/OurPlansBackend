package de.ait.todo.services.impl;

import de.ait.todo.dto.EventDTO;
import de.ait.todo.dto.EventsPage;
import de.ait.todo.dto.NewEventDTO;
import de.ait.todo.models.Event;
import de.ait.todo.models.User;
import de.ait.todo.repositories.EventsRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.EventsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EventsServiceImpl implements EventsService {

    private final static ModelMapper modelMapper = new ModelMapper();
    private final EventsRepository eventsRepository;

    private final UsersRepository usersRepository;

    @Override
    public EventDTO addEvent(Long currentUserID, NewEventDTO newEventDTO) {

        User user = usersRepository.findById(currentUserID)
                .orElseThrow(IllegalArgumentException::new);

        Event newEvent = Event.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .title(newEventDTO.getTitle())
                .description(newEventDTO.getDescription())
                .startAt(newEventDTO.getStartAt())
                .finishAt(newEventDTO.getFinishAt())
                .place(newEventDTO.getPlace())
                .category(newEventDTO.getCategory())
                .isBlocked(false)
                .build();
       // Event event = modelMapper.map(newEventDTO, Event.class);
       // event.setCteatedAt(LocalDateTime.now());
       // event.setAuthorId(currentUserID);

        eventsRepository.save(newEvent);
        return EventDTO.from(newEvent);   ///
    }

    @Override
    public EventsPage getAllEvents() {
        return EventsPage.builder()
                .events(from(eventsRepository.findAll()))
                .build();
    }
}
