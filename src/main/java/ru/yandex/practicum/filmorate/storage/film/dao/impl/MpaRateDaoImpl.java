package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.dao.MpaRateDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaRateDaoImpl implements MpaRateDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaRateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MpaRating getById(int id) {
        String sqlQuery = "SELECT * FROM mpa_rates WHERE mpa_rate_id = ?";
        List<MpaRating> mpaRatingList = jdbcTemplate.query(sqlQuery, this::makeMpaRating, id);
        return mpaRatingList.stream().findFirst().orElseThrow(() ->
                new EntityNotFoundException(HttpStatus.NOT_FOUND,
                        String.format("Рейтинга с ID %d не существует", id)));
    }

    @Override
    public List<MpaRating> getAll() {
        String sqlQuery = "SELECT * FROM mpa_rates";
        return jdbcTemplate.query(sqlQuery, this::makeMpaRating);
    }

    private MpaRating makeMpaRating(ResultSet rs, int rowNum) throws SQLException {
        return new MpaRating(rs.getInt("mpa_rate_id"), rs.getString("mpa_rate_name"));
    }
}