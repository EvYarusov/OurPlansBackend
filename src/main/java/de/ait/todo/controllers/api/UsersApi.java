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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Tags(value = {
        @Tag(name = "Users")
})
@RequestMapping("/api/users")
public interface UsersApi {

    @Operation(summary = "Получение своего профиля", description = "Доступно только аутентифицированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информацию о профиле",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProfileDto.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my/profile")
    ResponseEntity<ProfileDto> getProfile(@Parameter(hidden = true)
                                          @AuthenticationPrincipal AuthenticatedUser currentUser);

    @Operation(summary = "Получение списка своих задач", description = "Доступно только пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TasksPage.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/my/tasks")
    ResponseEntity<TasksPage> getMyTasks(@Parameter(hidden = true)
                                          @AuthenticationPrincipal AuthenticatedUser currentUser);

    @Operation(summary = "Получение списка всех пользователей", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UsersPage.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не уполномочен",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto")
                            )
                    }
            )
    })
    @GetMapping
    ResponseEntity<UsersPage> getAllUsers();

    @Operation(summary = "Получение пользователя по id", description = "Доступно только аутентифицированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto")
                            )
                    }
            )
    })
    @GetMapping("/{user_id}")
    ResponseEntity<UserDto> getUserById(Long userId,
                                        @Parameter(hidden = true)
                                        @AuthenticationPrincipal AuthenticatedUser currentUser);

    @Operation(summary = "Блокирование пользователя по id", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь заблокирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не уполномочен",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto")
                            )
                    }
            )
    })
    @PutMapping("/{user_id}/block")
    ResponseEntity<UserDto> blockUserById(Long userId);

    @Operation(summary = "Разблокирование пользователя по id", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь разблокирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не уполномочен",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto")
                            )
                    }
            )
    })
    @PutMapping("/{user_id}/unblock")
    ResponseEntity<UserDto> unblockUserById(Long userId);

}