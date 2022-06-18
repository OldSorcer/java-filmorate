package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void setLike(int userId, int filmId) {
        User user = userStorage.getUserById(userId);
        filmStorage.getFilmById(filmId).getLikes().add(user.getId());
    }

    public void deleteLike(int userId, int filmId) {
        User user = userStorage.getUserById(userId);
        filmStorage.getFilmById(filmId).getLikes().add(user.getId());
    }

    public List<Film> getPopularFilms(int count) {
        if (count == 0) {
            count = 10;
        }
        return filmStorage.getAll()
                .stream()
                .sorted(Comparator.comparingInt((Film f) -> f.getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }
}