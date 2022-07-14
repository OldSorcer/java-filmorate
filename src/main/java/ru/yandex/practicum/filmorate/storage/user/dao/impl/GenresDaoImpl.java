package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.user.dao.GenreDao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class GenresDaoImpl implements GenreDao {
    JdbcTemplate jdbcTemplate;

    public GenresDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(int id) {
        String query = "SELECT * FROM genres WHERE genre_id = ?";
        return jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("genre_name")), id);
    }

    @Override
    public Set<Genre> getFilmGenres(int filmId) {
        String query = "SELECT * FROM genres WHERE genre_id IN (SELECT GENRE_ID FROM FILMS_GENRES WHERE FILM_ID = ?);";
        return new HashSet<>(jdbcTemplate.query(query,
                (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("genre_name")), filmId));
    }

    @Override
    public Collection<Genre> getAll() {
        String query = "SELECT * FROM genres";
        return jdbcTemplate.query(query,
                (rs, rowNum) -> new Genre(rs.getInt("genre_id"), rs.getString("genre_name")));
    }
}
