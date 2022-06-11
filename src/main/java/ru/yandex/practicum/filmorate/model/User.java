package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    private String login;
    private String name;
    private String email;
    private int id;
    private LocalDate birthday;
    private Set<Integer> friendsList;
}