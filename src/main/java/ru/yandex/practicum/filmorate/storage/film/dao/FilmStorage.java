package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);
    void delete(Film film);
    Film update(Film film);
    List<Film> getAll();
    Film getFilmById(int id);
    List<Film> getPopularFilms(int count);
    List<Film> getCommonFilms(int userId, int friendId);
}
