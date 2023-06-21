package de.ait.todo.controllers;

import de.ait.todo.controllers.api.SignUpApi;
import de.ait.todo.dto.NewUserDto;
import de.ait.todo.dto.UserDto;
import de.ait.todo.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class SignUpController implements SignUpApi {

    private final SignUpService signUpService;

    @Override
    public ResponseEntity<UserDto> signUp(NewUserDto newUser) {
        return ResponseEntity
                .status(201)
                .body(signUpService.signUp(newUser));
    }
}
