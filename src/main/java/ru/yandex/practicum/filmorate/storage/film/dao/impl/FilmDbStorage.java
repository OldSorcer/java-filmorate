package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.GenresDao;
import ru.yandex.practicum.filmorate.storage.film.dao.LikesDao;
import ru.yandex.practicum.filmorate.storage.film.dao.MpaRateDao;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenresDao genresDao;
    private final MpaRateDao mpaRateDao;
    private final LikesDao likesDao;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenresDao genresDao, MpaRateDao mpaRateDao, LikesDao likesDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genresDao = genresDao;
        this.mpaRateDao = mpaRateDao;
        this.likesDao = likesDao;
    }

    @Override
    public Film add(Film film) {
        Validator.isValidFilm(film);
        String sqlQuery = "INSERT INTO films (film_name, description, release_date, duration, mpa_rate_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        int filmId = keyHolder.getKey().intValue();
        film.setId(filmId);
        if (Objects.nonNull(film.getGenres())) {
            genresDao.addFilmGenres(film.getGenres(), filmId);
        }
        return film;
    }

    @Override
    public void delete(Film film) {
        String sqlQuery = "DELETE FROM films WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    @Override
    public Film update(Film film) {
        Film findFilm = getFilmById(film.getId());
        String sqlQuery = "UPDATE films SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_rate_id = ? WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), Date.valueOf(film.getReleaseDate()), film.getDuration(), film.getMpa().getId(), film.getId());
        if (film.getGenres().isEmpty()) {
            genresDao.removeGenres(film.getId());
        } else {
            genresDao.removeGenres(film.getId());
            genresDao.addFilmGenres(film.getGenres(), film.getId());
        }
        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "SELECT * FROM films";
        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }

    @Override
    public Film getFilmById(int id) {
        String sqlQuery = "SELECT * FROM films WHERE film_id = ?";
        List<Film> filmList = jdbcTemplate.query(sqlQuery, this::makeFilm, id);
        return filmList.stream().findFirst().orElseThrow(() ->
                new EntityNotFoundException(HttpStatus.NOT_FOUND,
                        String.format("Фильм с ID %d не найден", id)));
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setName(rs.getString("film_name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        film.setId(rs.getInt("film_id"));
        film.setGenres(genresDao.getByFilmId(film.getId()));
        film.setMpa(mpaRateDao.getById(rs.getInt("mpa_rate_id")));
        film.setLikes(Set.copyOf(likesDao.getLikes(film.getId())));
        return film;
    }
}