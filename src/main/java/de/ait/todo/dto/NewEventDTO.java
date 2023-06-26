package de.ait.todo.dto;

import java.text.spi.DateFormatProvider;
import java.time.LocalDateTime;

public class NewEventDTO {
    private LocalDateTime cteatedAt;

    private String title;

    private String description;

    private LocalDateTime startAt;

    private LocalDateTime finishAt;

    private String place;

    private String category;
}
