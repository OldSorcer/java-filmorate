package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film implements Comparable<Film>{
    private String name;
    private String description;
    private int id;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes = new HashSet<>();
    private List<Genre> genres = new ArrayList<>();
    private MpaRating mpa;
    private List<Director> directors;
    @Override
    public int compareTo(Film o) {
        return o.getLikes().size() - this.getLikes().size();
    }
}