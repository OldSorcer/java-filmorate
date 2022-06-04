package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Film {
    private String name;
    private String description;
    private int id;
    private LocalDate releaseDate;
    private int duration;
    public final static int MAX_DESCRIPTION_LENGTH = 200;
    public final static LocalDate LATEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
}