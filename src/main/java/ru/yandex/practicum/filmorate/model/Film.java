package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private String name;
    private String description;
    private int id;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes = new HashSet<>();

    public Film() {
    }

    public Film(String name, String description, int id, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
    }
}