package ru.yandex.practicum.filmorate.storage.film.dao.impl;

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
import ru.yandex.practicum.filmorate.storage.user.dao.GenreDao;
import ru.yandex.practicum.filmorate.storage.user.dao.MpaDao;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;
    private final MpaDao mpaDao;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDao genreDao, MpaDao mpaDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
        this.mpaDao = mpaDao;
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
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
       String queryForGenres = "MERGE INTO films_genres (film_id, genre_id) KEY (film_id, genre_id) VALUES (?, ?)";
        int filmId = keyHolder.getKey().intValue();
        /*for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(queryForGenres, filmId, genre.getId());
        }*/
        String queryForFilm = "SELECT * FROM films WHERE film_id = ?";
        return jdbcTemplate.queryForObject(queryForFilm, this::makeFilm, filmId);
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
        String queryForUpdate = "UPDATE films SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_rate_id = ? WHERE film_id = ?";
        jdbcTemplate.update(queryForUpdate,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        String queryForSearchUpdatedFilm = "SELECT * FROM films WHERE film_id = ?";
        return film;
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
        film.setMpa(mpaDao.getById(film.getId()));
        film.setGenres(Set.copyOf(genreDao.getFilmGenres(film.getId())));
        String queryForLikes = "SELECT user_id FROM films_likes WHERE film_id = ?;";
        List<Integer> likes = jdbcTemplate.query(queryForLikes, (resultSet, rNum) -> resultSet.getInt("user_id"), film.getId());
        if (!likes.isEmpty()) film.setLikes(new HashSet<>(likes));
        return film;
    }
}
