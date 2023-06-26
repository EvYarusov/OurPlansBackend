package de.ait.todo.dto;

import de.ait.todo.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Зарегистрированный пользователь")
public class UserDto {

    @Schema(description = "идентификатор пользователя", example = "1")
    private Long id;

    /*@Schema(description = "электронная почта", example = "my@mail.co")
    private String email;*/

    @Schema(description = "имя пользователя", example = "username")
    private String userName;

    private boolean isBlocked;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .isBlocked(user.isBlocked())
                .build();
    }
//.userName( user.getUserName().equals("")  ? ("user" + user.getId()) : user.getUserName() )

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}
