package ru.yandex.practicum.filmorate.storage.film.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film add(Film film) {
        Validator.isValidFilm(film);
        String query = "INSERT INTO films (film_name, description, release_date, duration, mpa_rate_id) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] {"film_id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getMpaRate().getId());
            return ps;
        }, keyHolder);
        int userId = keyHolder.getKey().intValue();
        String findUserQuery = "SELECT * FROM films WHERE film_id = ?";
        return jdbcTemplate.queryForObject(findUserQuery, this::makeFilm, userId);
    }

    @Override
    public void delete(Film film) {
        String query = "DELETE FROM films WHERE film_id = ?";
        jdbcTemplate.update(query, film.getId());
    }

    @Override
    public Film update(Film film) {
        String queryForSearch = "SELECT * FROM films WHERE film_id = ?";
        Film findedFilm = jdbcTemplate.queryForObject(queryForSearch, this::makeFilm, film.getId());
        if (Objects.isNull(findedFilm)) {
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND,
                    String.format("Фильма с ID %d не существует", film.getId()));
        }
        String queryForUpdate = "UPDATE films SET film_name = ?, description = ?, release_date = ?, duration = ? WHERE film_id = ?";
        jdbcTemplate.update(queryForUpdate,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getId());
        String queryForSearchUpdatedUser = "SELECT * FROM films WHERE film_id = ?";
        return jdbcTemplate.queryForObject(queryForSearchUpdatedUser,this::makeFilm, film.getId());
    }

    @Override
    public List<Film> getAll() {
        String query = "SELECT * FROM FILMS;";
        return jdbcTemplate.query(query, this::makeFilm);
    }

    @Override
    public Film getFilmById(int id) {
        String query = "SELECT * FROM films WHERE film_id = ?";
        return jdbcTemplate.queryForObject(query, this::makeFilm, id);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt("film_id"));
        film.setName(rs.getString("film_name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        String queryForMPA = "SELECT * FROM mpa_rates WHERE mpa_rate_id = ?";
        MpaRating mpaRating = jdbcTemplate.queryForObject(queryForMPA, (rset, rn) ->
                new MpaRating(rset.getInt("mpa_rate_id"), rset.getString("mpa_rate_name")),
                rs.getInt("mpa_rate_id"));
        film.setMpaRate(mpaRating);
        String queryForGenres = "SELECT * FROM genres " +
                "WHERE genre_id IN (SELECT genre_id FROM films_genres WHERE film_id = ?)";
        List<Genre> genre = jdbcTemplate.query(queryForGenres, (resultSet, rowNums) ->
                new Genre(resultSet.getInt("genre_id"), resultSet.getString("genre_name")));
        film.setGenres(new HashSet<>(genre));
        String queryForLikes = "SELECT user_id FROM films_likes WHERE film_id = ?";
        List<Integer> likes = jdbcTemplate.queryForList(queryForLikes, Integer.class);
        film.setLikes(new HashSet<>(likes));
        return film;
    }
}
