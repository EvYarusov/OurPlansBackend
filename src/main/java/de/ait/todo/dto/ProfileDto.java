package de.ait.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {

    private Long id;

    private String email;

    private String role;

    private String userName;

    private String fullName;

    private int age;

    private String gender;

    private boolean isBlocked;

}
