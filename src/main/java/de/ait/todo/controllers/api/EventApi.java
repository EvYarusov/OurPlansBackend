package de.ait.todo.controllers.api;

import de.ait.todo.dto.*;
import de.ait.todo.security.details.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
        @Tag(name = "Events")
})
@RequestMapping("api/events")
@ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
        content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(ref = "StandardResponseDTO"))
        }
)
public interface EventApi {

    @Operation(summary = "Добавить мероприятие", description = "Доступно только зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Мероприятие добавлено",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventDTO.class))
            })
    })
    @PostMapping
    ResponseEntity<EventDTO> addEvent(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user,
                                      @RequestBody NewEventDTO newEventDTO);

    @Operation(summary = "Получение списка всех мероприятий", description = "Доступно зарегистрированному пользователю и администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с мероприятиями",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventsPage.class))
                    }
            )
    })
    @GetMapping
    ResponseEntity<EventsPage> getAllEvents();

    @Operation(summary = "Получение мероприятия по Id", description = "Доступно администратору, зарегистрированному пользователю, незарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятие",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/{event-id}")
    ResponseEntity<EventDTO> getEventById(@Parameter(description = "идентификатор мероприятие") @PathVariable("event-id") Long eventId);

    @Operation(summary = "посмотреть мероприятия, созданные конкретным пользователем", description = "Доступно администратору, зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятия",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventsPage.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/byUser/{user_id}")
    ResponseEntity<EventsPage> getEventsByUserId(@Parameter(description = "идентификатор пользователя") @PathVariable("user_id") Long userId);
}
