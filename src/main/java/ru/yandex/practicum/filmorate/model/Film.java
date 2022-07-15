package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film implements Comparable<Film>{
    private String name;
    private String description;
    private int id;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes = new HashSet<>();
    private List<Genre> genres = new ArrayList<>();
    private MpaRating mpa;

    public Film() {
    }

    public Film(String name, String description, int id, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(String name, String description, int id, LocalDate releaseDate, int duration, List<Genre> genres, MpaRating mpaRate) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpaRate;
    }

    @Override
    public int compareTo(Film o) {
        return o.getLikes().size() - this.getLikes().size();
    }
}