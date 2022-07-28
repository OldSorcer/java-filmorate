package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorDao {
    List<Director> getAll();
    Director getById(int id);
    Director add(Director director);
    Director update(Director director);
    void deleteById(int id);
    List<Director> getByFilmId(int filmId);
    void addFilmDirectors(List<Director> directorList, int filmId);
    void deleteFilmDirectors(int filmId);
}
