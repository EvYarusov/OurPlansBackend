package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;

@Schema(description = "Зарегистрированный пользователь")
public class NewEventDTO {
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
