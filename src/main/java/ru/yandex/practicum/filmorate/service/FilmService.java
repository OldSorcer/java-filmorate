package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void setLike(int userId, int filmId) {
        filmStorage.getAll().get(filmId).getLikes().add(userId);
    }

    public void deleteLike(int userId, int filmId) {
        filmStorage.getAll().get(filmId).getLikes().remove(userId);
    }

    public List<Film> getPopularFilms() {
        return filmStorage.getAll()
                .stream()
                .sorted(Comparator.comparingInt((Film f) -> f.getLikes().size()).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}