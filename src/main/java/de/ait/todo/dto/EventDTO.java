package de.ait.todo.dto;

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
public class EventDTO {
    private Long id;

    private LocalDateTime cteatedAt;

    private int authorId;

    private String title;

    private String description;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    private String place;

    private String category;

}
