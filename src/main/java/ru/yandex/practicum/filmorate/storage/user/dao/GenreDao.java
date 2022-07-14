package ru.yandex.practicum.filmorate.storage.user.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GenreDao {
    Genre getById(int id);
    Set<Genre> getFilmGenres(int filmId);
    Collection<Genre> getAll();
}
