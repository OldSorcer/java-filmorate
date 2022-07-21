package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.film.dao.DirectorDao;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DirectorDaoImpl implements DirectorDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DirectorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Director> getAll() {
        String sqlQuery = "SELECT * FROM directors";
        return jdbcTemplate.query(sqlQuery, this::makeDirector);
    }

    @Override
    public Director getById(int id) {
        String sqlQuery = "SELECT * FROM directors WHERE director_id = ?";
        try {
            return jdbcTemplate.queryForObject(sqlQuery, this::makeDirector, id);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND,
                    String.format("Режиссера с ID %d не существует", id));
        }
    }

    @Override
    public Director add(Director director) {
        String sqlQuery = "INSERT INTO directors (director_name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[] {"director_id"});
            ps.setString(1, director.getName());
            return ps;
        }, keyHolder);
        director.setId(keyHolder.getKey().intValue());
        return director;
    }

    @Override
    public Director update(Director director) {
        String sqlQuery = "UPDATE directors SET director_name = ? WHERE director_id = ?";
        jdbcTemplate.update(sqlQuery, director.getName(), director.getId());
        return director;
    }

    @Override
    public void deleteById(int id) {
        String sqlQuery = "DELETE FROM directors WHERE director_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<Director> getByFilmId(int filmId) {
        String sqlQuery = "SELECT * FROM directors " +
                "WHERE director_id IN (SELECT director_id FROM films_directors WHERE film_id = ?)";
        return jdbcTemplate.query(sqlQuery, this::makeDirector, filmId);
    }

    @Override
    public void addFilmDirectors(List<Director> directors, int filmId) {
        String sqlQuery = "MERGE INTO films_directors (film_id, director_id) " +
                "KEY (film_id, director_id) " +
                "VALUES (?, ?)";
        for (Director director : directors) {
            jdbcTemplate.update(sqlQuery, filmId, director.getId());
        }
    }

    @Override
    public void deleteFilmDirectors(int filmId) {
        String sqlQuery = "DELETE FROM films_directors WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId);
    }

    private Director makeDirector(ResultSet rs, int rowNum) throws SQLException {
        Director director = new Director();
        director.setId(rs.getInt("director_id"));
        director.setName(rs.getString("director_name"));
        return director;
    }
}
