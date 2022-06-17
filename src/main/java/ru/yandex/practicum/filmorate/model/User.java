package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
public class User {
    private String login;
    private String name;
    private String email;
    private int id;
    private LocalDate birthday;
    private Set<Integer> friendsList = new HashSet<>();
    public User() {
    }

    public User(String login, String name, String email, int id, LocalDate birthday) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.id = id;
        this.birthday = birthday;
        this.friendsList = friendsList;
    }
}