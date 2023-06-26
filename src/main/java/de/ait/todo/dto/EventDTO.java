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

    private Long id;

    private int authorId;

    private String title;

    private String description;

    private String startAt;

    private String finishAt;

    private String place;

    private String category;

    private String isBlocked;

}
