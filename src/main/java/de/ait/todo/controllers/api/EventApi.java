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

import java.util.List;

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


    @Operation(summary = "заблокировать/разблокировать мероприятие", description = "Доступно администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вы заблокировали мероприятие",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventDTO.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @PutMapping("/{event_id}/block")
    ResponseEntity<EventDTO> eventBlock(@Parameter(description = "идентификатор мероприятия")
                                        @PathVariable("event_id") Long eventId,
                                        @Parameter(description = "Статус блокировки") @RequestParam Boolean isBlock);


    @Operation(summary = "Посмотреть всех пользователей зарегистрировавшихся на мероприятие", description = "Доступно администратору, зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователи, зарегистрированные на мероприятие",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsersPage.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/{event_id}/members")
    ResponseEntity<List<UserDto>> getMembersByEventId(@Parameter(description = "идентификатор мероприятие") @PathVariable("event_id") Long eventId);

    @Operation(summary = "Зарегистрироваться на мероприятие", description = "Доступно только зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вы зарегистрированы под номером",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))
                    })
    })
    @PostMapping("/{event_id}/members/me")
    ResponseEntity<Integer> takePartInEvent(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                            @Parameter(description = "идентификатор мероприятие") @PathVariable("event_id") Long eventId);


    @Operation(summary = "покинуть мероприятие", description = "Доступно зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вы покинули мероприятие",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Integer.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    })
    })
    @PutMapping("{event_id}/members/me")
    ResponseEntity<Integer> eventOut(@Parameter (hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                     @Parameter(description = "идентификатор мероприятия")
                                     @PathVariable("event_id") Long eventId);

    @Operation(summary = "посмотреть мероприятия по месту", description = "Доступно администратору, зарегистрированному и незарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятия",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventsPage.class))
                    }
            ),
    })

    @GetMapping("/by_place/{place}")
    ResponseEntity<EventsPage> getEventsByPlace(@Parameter(description = "место") @PathVariable("place") String place);

    @Operation(summary = "посмотреть мероприятия по категории", description = "Доступно администратору, зарегистрированному и незарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятия",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventsPage.class))
                    }
            ),
    })
    @GetMapping("/by_category/{category}")
    ResponseEntity<EventsPage> getEventsByCategory(@Parameter(description = "категория") @PathVariable("category") String category);

    @Operation(summary = "посмотреть созданные мной мероприятия", description = "Доступно зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Мероприятия",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventsPage.class))
                    }
            ),
    })
    @GetMapping("author/me")
    ResponseEntity<EventsPage> getEventsCreatedByMe(@Parameter (hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

//    @Operation(summary = "посмотреть мероприятия с моим участием", description = "Доступно зарегистрированному пользователю")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Мероприятия",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema = @Schema(implementation = EventsPage.class))
//                    }
//            ),
//    })
//    @GetMapping("author/me")
//    ResponseEntity<EventsPage> getEventsWereIAmMember(@Parameter (hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser);

//    @Operation(summary = "редактировать мероприятие", description = "Доступно зарегистрированному пользователю, создавшему мероприятие; администратору")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Вы заблокировали мероприятие",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema = @Schema(implementation = EventDTO.class))
//                    }
//            ),
//            @ApiResponse(responseCode = "404", description = "Мероприятие не найдено",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema = @Schema(ref = "StandardResponseDto"))
//                    }
//            )
//    })
//    @PutMapping("{event_id}/update")
//    ResponseEntity<EventDTO> editEvent(@Parameter (hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
//                                        @Parameter(description = "идентификатор мероприятия") @PathVariable("event_id") Long eventId);


}
