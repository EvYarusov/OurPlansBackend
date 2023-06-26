package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Зарегистрированный пользователь")
public class EventDTO {
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private Long id;
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private LocalDateTime cteatedAt;
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private int authorId;

    @Schema(description = "Название мероприятия", example = "Volleyball")

    private String title;
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private String description;
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private LocalDateTime startAt;
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private LocalDateTime finishAt;
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private String place;
    @Schema(description = "Название мероприятия", example = "Volleyball")
    private String category;

}
