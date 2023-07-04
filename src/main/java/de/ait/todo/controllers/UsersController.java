package de.ait.todo.controllers;

import de.ait.todo.controllers.api.UsersApi;
import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.UserDto;
import de.ait.todo.dto.UsersPage;
import de.ait.todo.security.details.AuthenticatedUser;
import de.ait.todo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UsersService usersService;


    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<ProfileDto> getProfile(AuthenticatedUser currentUser) {
        Long currentUserId = currentUser.getUser().getId();
        ProfileDto profile = usersService.getProfile(currentUserId);

        return ResponseEntity.ok(profile);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<UsersPage> getAllUsers() {

        return ResponseEntity.ok(usersService.getAllUsers());
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<UserDto> getUserById(Long userId, AuthenticatedUser currentUser) {

        Long currentUserId = currentUser.getUser().getId();

        return ResponseEntity.ok(usersService.getUserById(userId, currentUserId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<UserDto> blockUserById(Long userId) {

        return ResponseEntity.ok(usersService.blockUserById(userId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<UserDto> unblockUserById(Long userId) {

        return ResponseEntity.ok(usersService.unblockUserById(userId));
    }

}
