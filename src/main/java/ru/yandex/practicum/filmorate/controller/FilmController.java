package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Validator;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private Map<Integer, Film> filmList = new HashMap<>();
    private int idCounter = 1;

    @PostMapping
    public Film add(@RequestBody Film film) {
        if (filmList.containsValue(film)) {
            log.warn("Ошибка добавления фильма.Такой фильм уже существует");
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Такой фильм уже существует");
        } else if (Validator.isValidFilm(film)) {
            film.setId(idCounter++);
            filmList.put(film.getId(), film);
            log.info("Успешно добален фильм с ID" + film.getId());
        }
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (!filmList.containsKey(film.getId())) {
            log.warn("Ошибка обновления фильма. Такого фильма не существует");
            throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Такого фильма не существует");
        } else if (Validator.isValidFilm(film)) {
            filmList.put(film.getId(), film);
            log.info("Успешно обновлен фильм с ID" + film.getId());
        }
        return film;
    }

    @GetMapping
    public List<Film> getAll() {
        return List.copyOf(filmList.values());
    }
}