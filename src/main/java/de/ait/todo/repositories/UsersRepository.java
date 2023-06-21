package de.ait.todo.repositories;

import de.ait.todo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsById(Long id);
}
