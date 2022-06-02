package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmDoesNotExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    Map<Integer, Film> filmList = new HashMap<>();
    int idCounter = 1;

    @PostMapping
    public Film add(@RequestBody Film film) throws FilmAlreadyExistException, ValidationException {
        if (filmList.containsValue(film)) {
            log.warn("Ошибка добавления фильма.Такой фильм уже существует");
            throw new FilmAlreadyExistException("Такой фильм уже существует");
        } else if (!isValid(film)) {
            log.warn("Ошибка валидации данных фильма.");
            throw new ValidationException("Ошибка валидации данных. Проверьте введенные значения и повторите попытку.");
        }
        film.setId(idCounter++);
        filmList.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws FilmDoesNotExistException, ValidationException {
        if (!filmList.containsKey(film.getId())) {
            log.warn("Ошибка обновления фильма. Такого фильма не существует");
            throw new FilmDoesNotExistException("Такого фильма не существует");
        } else if (!isValid(film)) {
            log.warn("Ошибка валидации данных фильма.");
            throw new ValidationException("Ошибка валидации данных. Проверьте введенные значения и повторите попытку");
        }
        filmList.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> getAll() {
        return List.copyOf(filmList.values());
    }

    public boolean isValid(Film film) throws ValidationException {
        return !film.getName().isEmpty() &&
                (film.getDescription().length() <= Film.MAX_DESCRIPTION_LENGTH) &&
                !film.getReleaseDate().isBefore(Film.LATEST_RELEASE_DATE) &&
                !(film.getDuration() < 0);
    }
}