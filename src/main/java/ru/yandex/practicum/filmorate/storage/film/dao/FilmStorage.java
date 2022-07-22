package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);
    void delete(Film film);
    Film update(Film film);
    List<Film> getAll();
    Film getFilmById(int id);
    List<Film> getFilmsByDirectorId(int directorId, String sortedBy);
    List<Film> getPopularFilms(int count, int genreId, int year);
    List<Film> getPopularFilmsNonGenresYear(int count);
    List<Film> getPopularFilmsNonYear(int count, int genreId);
    List<Film> getPopularFilmsNonGenre(int count, int year);
}