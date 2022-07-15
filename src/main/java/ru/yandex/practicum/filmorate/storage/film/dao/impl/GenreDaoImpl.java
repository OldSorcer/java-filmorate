package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class GenreDaoImpl implements GenresDao{
    JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getById(int id) {
        String sqlQuery = "SELECT * FROM genres WHERE genre_id = ?";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, this::makeGenre, id);
        return genres.stream().findFirst().orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND,
                String.format("Жанра с ID %d не существует", id)));
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlQuery, this::makeGenre);
    }

    @Override
    public List<Genre> getByFilmId(int filmId) {
        String sqlQuery = "SELECT * FROM genres WHERE genre_id IN (SELECT genre_id FROM films_genres WHERE film_id = ?)";
        return jdbcTemplate.query(sqlQuery, this::makeGenre, filmId);
    }

    @Override
    public void addFilmGenres(List<Genre> genres, int filmId) {
        String sqlQuery = "MERGE INTO films_genres (film_id, genre_id) VALUES (?, ?)";
        List<Genre> uniqueGenre = genres.stream().distinct().collect(Collectors.toList());
        for (Genre genre : genres) {
            jdbcTemplate.update(sqlQuery, filmId, genre.getId());
        }
    }

    @Override
    public void removeGenres(int filmId) {
        String sqlQuery = "DELETE FROM films_genres WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("name"));
        return genre;
    }
}