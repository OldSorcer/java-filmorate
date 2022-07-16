package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenresDao {
    Genre getById(int id);
    List<Genre> getAll();
    List<Genre> getByFilmId(int filmId);
    void addFilmGenres(List<Genre> genres, int filmId);
    void removeGenres(int filmId);
}
