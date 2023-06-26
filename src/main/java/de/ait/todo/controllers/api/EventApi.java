package de.ait.todo.controllers.api;

import de.ait.todo.dto.EventDTO;
import de.ait.todo.dto.NewEventDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    ResponseEntity<Long> addEvent(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user,
                                      @RequestBody NewEventDTO newEventDTO);

}
