package de.ait.todo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class User {

    public enum Role {
        USER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String userName;

    private String fullName;

    private int age;

    private String gender;

    private boolean isBlocked;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @ManyToMany(mappedBy = "members")
    private List<Event> memberEvents; // список мероприятий, в которых он участвует

    @OneToMany(mappedBy = "owner")
    private List<Event> ownerEvents; // список мероприятий, которые он создал
}
