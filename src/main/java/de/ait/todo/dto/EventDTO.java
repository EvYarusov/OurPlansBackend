package de.ait.todo.dto;

import de.ait.todo.models.Event;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Зарегистрированный пользователь")
public class EventDTO {

    private Long id;

   // private User user;

    private String title;

    private String description;

    private String startAt;

    private String finishAt;

    private String place;

    private String category;

    private Boolean isBlocked;

    public static EventDTO from (Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startAt(event.getStartAt())
                .finishAt(event.getFinishAt())
                .place(event.getPlace())
                .category(event.getCategory())
                .isBlocked(event.getIsBlocked())
                .build();
    }
    public static List<EventDTO> from(List<Event> events) {
        return events.stream().map(EventDTO::from).collect(Collectors.toList());
    }
}
