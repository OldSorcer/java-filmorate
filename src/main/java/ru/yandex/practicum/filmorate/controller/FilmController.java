package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    FilmService filmService;

    public FilmController() {

    }

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film add(@RequestBody Film film) {
        log.info("Получен POST запрос к эндпоинту /films");
        return filmService.add(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("Получен PUT запрос к эндпоинту /films");
        return filmService.update(film);
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Получен GET запрос к эндпоинту /films");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получен GEt запрос к эндпоинту /films/{}", id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void setLike(@PathVariable int id,
                        @PathVariable int userId) {
        log.info("Получен PUT запрос к эндпоинту /{}/like/{}", id, userId);
        filmService.setLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id,
                           @PathVariable int userId) {
        log.info("Получен DELETE запрос к эндпоинту /{}/like/{}", id, userId);
        filmService.deleteLike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(value = "count", defaultValue = "10", required = false) int count,
                                      @RequestParam(value = "genreId", defaultValue = "0", required = false) int genreId,
                                      @RequestParam(value = "year", defaultValue = "0", required = false) int year) {
        log.info("Получен GET запрос к эндпоинту /popular?count={limit}&genreId={genreId}&year={year}");
        return filmService.getPopularFilms(count, genreId, year);
    }

    @GetMapping("/director/{directorId}")
    public List<Film> getFilmsByDirectorId(@PathVariable int directorId,
                                           @RequestParam String sortBy) {
        return filmService.getFilmsByDirectorId(directorId, sortBy);
    }

    @DeleteMapping("/{id}")
    public void deleteFilmById(@PathVariable int id) {
        log.info("Получен DELETE запрос к эндпоинту /users/{}", id);
        filmService.deleteFilmById(id);
    }

    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam int userId, @RequestParam int friendId) {
        log.info("Получен GET запрос к эндпоинту /common");
        return filmService.getCommonFilms(userId, friendId);
    }
}