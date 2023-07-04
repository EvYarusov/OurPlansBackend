package de.ait.todo.services.impl;

import de.ait.todo.dto.*;
import de.ait.todo.exceptions.NotFoundException;
import de.ait.todo.models.Event;
import de.ait.todo.models.User;
import de.ait.todo.repositories.EventsRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.EventsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import static de.ait.todo.dto.EventDTO.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                .owner(user)
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
        return from(newEvent);   ///
    }

    @Override
    public EventsPage getAllEvents() {
        return EventsPage.builder()
                .events(from(eventsRepository.findAll()))
                .build();
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдена")
        );
        return from(event);
    }

    @Override
    public EventsPage getEventsByUserId(Long userId) {
//        User user = usersRepository.findById(userId).orElseThrow(
//                () -> new NotFoundException("Пользователь <" + userId + "> не найден")
//        );

        return EventsPage.builder()
                .events(from(eventsRepository.findAllByOwner_Id(userId))) ///
                .build();
    }

    @Override
    public EventDTO eventBlock(Long eventId, Boolean isBlock) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдено")
        );
        event.setIsBlocked(isBlock);
        eventsRepository.save(event);
        return from(event);
    }

    @Override
    public List<UserDto> getMembersByEventId(Long eventId) {
        // Event event = eventsRepository.findByIdAndIsBlocked(eventId, false).get(); почему отдает Лист?
        Event event = eventsRepository.findById(eventId).get();
        List<User> usersForEvent = event.getMembers();
        List<UserDto> usersDtoForEvent = modelMapper.map(usersForEvent, new TypeToken<List<UserDto>>(){}.getType());
        return usersDtoForEvent;
    }

    @Override
    public Integer takePartInEvent(AuthenticatedUser authenticatedUser, Long eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдено")
        );
        User user = usersRepository.findById(authenticatedUser.getUser().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь <" + authenticatedUser.getUser().getId() + "> не найден")  // какая нужна ошибка?
        );
        event.getMembers().add(user);
        eventsRepository.save(event);
        return event.getMembers().size();
    }

    @Override
    public Integer eventOut(AuthenticatedUser authenticatedUser, Long eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдено")
        );
        event.getMembers().remove(authenticatedUser);
        eventsRepository.save(event);
        return event.getMembers().size();
    }

    @Override
    public EventsPage getEventsByPlace(String place) {
        return EventsPage.builder()
                .events(from(eventsRepository.findAllByPlace(place))) //
                .build();
    }

    @Override
    public EventsPage getEventsByCategory(String category) {
        EventsPage eventsPage =EventsPage.builder()
                .events(from(eventsRepository.findAllByCategory(category))) //
                .build();
        return eventsPage;

    }

    @Override
    public EventsPage getEventsCreatedByMe(AuthenticatedUser authenticatedUser) {
        return getEventsByUserId(authenticatedUser.getUser().getId());
    }

    @Override
    public EventsPage getEventsWereIAmMember(AuthenticatedUser authenticatedUser) {
        User user = usersRepository.findById(authenticatedUser.getUser().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь <" + authenticatedUser.getUser().getId() + "> не найден"));
        return EventsPage.builder()
                .events(from(eventsRepository.findByMembersContaining(user)))
                .build();
    }


}
