package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    private String name;
    private String description;
    private int id;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes;
}