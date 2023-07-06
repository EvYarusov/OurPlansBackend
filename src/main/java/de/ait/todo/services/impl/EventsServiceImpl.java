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
import static de.ait.todo.dto.UserDto.*;

import java.time.LocalDateTime;
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

        eventsRepository.save(newEvent);
        return from(newEvent);
    }

    @Override
    public EventsPage getAllEvents() {

        return EventsPage.builder()
                .events(from(eventsRepository.findAllByIsBlockedFalse()))
                .build();
    }

    @Override
    public EventDTO getEventById(Long eventId) {
        Event event = eventsRepository.findByIdAndIsBlockedFalse(eventId)
                .orElseThrow(() -> new NotFoundException("Мероприятие <" + eventId + "> не найдено")
        );
        return from(event);
    }

    @Override
    public EventsPage getEventsByOwnerId(Long userId) {
//        User user = usersRepository.findById(userId).orElseThrow(
//                () -> new NotFoundException("Пользователь <" + userId + "> не найден")
//        );

        return EventsPage.builder()
 //               .events(from(eventsRepository.findAllByOwner_Id(userId)))
                .events(from(eventsRepository.findAllByOwner_IdAndIsBlockedFalse(userId)))
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
    public Long takePartInEvent(AuthenticatedUser authenticatedUser, Long eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдено")
        );
        User user = usersRepository.findById(authenticatedUser.getUser().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь <" + authenticatedUser.getUser().getId() + "> не найден")
        );
        event.getMembers().add(user);
        eventsRepository.save(event);
        return user.getId();
    }

    @Override
    public Long eventOut(AuthenticatedUser authenticatedUser, Long eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдено")
        );
        User user = usersRepository.findById(authenticatedUser.getUser().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь <" + authenticatedUser.getUser().getId() + "> не найден")
        );
        List<User> members = event.getMembers();
        members.remove(user);
        event.setMembers(members);
        eventsRepository.save(event);
        return user.getId();
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
        return getEventsByOwnerId(authenticatedUser.getUser().getId());
    }

    @Override
    public EventsPage getEventsWereIAmMember(AuthenticatedUser authenticatedUser) {
        User user = usersRepository.findById(authenticatedUser.getUser().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь <" + authenticatedUser.getUser().getId() + "> не найден"));
        return EventsPage.builder()
                .events(from(eventsRepository.findByMembersContains(user)))
                .build();
    }

    @Override
    public EventDTO updateEvent(AuthenticatedUser authenticatedUser, Long eventId, NewEventDTO newEventDTO) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдено"));
        User user = usersRepository.findById(authenticatedUser.getUser().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь <" + authenticatedUser.getUser().getId() + "> не найден"));
        if (event.getOwner().getId().equals(user.getId()) || user.getRole().equals(User.Role.ADMIN)) {

            event.setTitle(newEventDTO.getTitle());
            event.setDescription(newEventDTO.getDescription());
            event.setStartAt(newEventDTO.getStartAt());
            event.setFinishAt(newEventDTO.getFinishAt());
            event.setPlace(newEventDTO.getPlace());
            event.setCategory(newEventDTO.getCategory());

            eventsRepository.save(event);
        } else throw new NotFoundException("Вам не доступно редактирование мероприятия <" + eventId + ">");

        return getEventById(eventId);
    }

    @Override
    public StringPage getAllCategory() {
        return StringPage.builder()
                .strings(eventsRepository.getCategories())
                .build();
    }

    @Override
    public StringPage getAllPlaces() {

//        HashSet<String> places = new HashSet<>();
//        List<EventDTO> allEvents = getAllEvents().getEvents();
//        for (EventDTO event : allEvents) {
//            places.add(event.getPlace());
//        }
//        List<String> placesList = new ArrayList<>(places);
//        return StringPage.builder()
//               .strings(placesList)
//               .build();

        return StringPage.builder()
                .strings(eventsRepository.getPlaces())
                .build();
    }

    @Override
    public void deleteEvent(AuthenticatedUser authenticatedUser, Long eventId) {
        Event event = eventsRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + eventId + "> не найдено"));
        User user = usersRepository.findById(authenticatedUser.getUser().getId()).orElseThrow(
                () -> new NotFoundException("Пользователь <" + authenticatedUser.getUser().getId() + "> не найден"));
        if (event.getOwner().getId().equals(user.getId())) {
            eventsRepository.deleteById(eventId);
        } else throw new NotFoundException("Вам не доступно редактирование мероприятия <" + eventId + ">");

    }

    @Override
    public EventsPage allEventsOfUserBlock(Long userId, Boolean isBlock) {
        User user = usersRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Мероприятие <" + userId + "> не найдено"));
        List<Event> events = eventsRepository.findAllByOwner_Id(userId);
        for (Event event : events) {
            event.setIsBlocked(isBlock);
        }
        eventsRepository.saveAll(events);
        return EventsPage.builder()
                .events(from(eventsRepository.findAllByOwner_Id(userId)))
                .build();
    }


}
