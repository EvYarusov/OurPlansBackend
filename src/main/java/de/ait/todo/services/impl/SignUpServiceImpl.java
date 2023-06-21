package de.ait.todo.services.impl;

import de.ait.todo.dto.NewUserDto;
import de.ait.todo.dto.UserDto;
import de.ait.todo.models.User;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static de.ait.todo.dto.UserDto.from;


@RequiredArgsConstructor
@Service
public class SignUpServiceImpl implements SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(NewUserDto newUser) {
        User user = User.builder()
                .email(newUser.getEmail())
                .hashPassword(passwordEncoder.encode(newUser.getPassword()))
                .role(User.Role.USER)
                .build();

        usersRepository.save(user);

        return from(user);
    }
}
