package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Зарегистрированный пользователь")
public class NewEventDTO {
    @Schema(description = "Название мероприятия", example = "Volleyball", required = true)
    private String title;
    @Schema(description = "Описание мероприятия", example = "The winner gets a beer")
    private String description;
    @Schema(description = "Дата и время начало", example = "26.06.2023 11:00")
    private String startAt;
    @Schema(description = "Дата и время окончание", example = "26.06.2023 13:00")
    private String finishAt;
    @Schema(description = "Место проведения", example = "Green street, 15")
    private String place;
    @Schema(description = "Категория", example = "Sport")
    private String category;
}
