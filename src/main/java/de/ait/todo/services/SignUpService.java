package de.ait.todo.services;


import de.ait.todo.dto.NewUserDto;
import de.ait.todo.dto.UserDto;

public interface SignUpService {
    UserDto signUp(NewUserDto newUser);
}
