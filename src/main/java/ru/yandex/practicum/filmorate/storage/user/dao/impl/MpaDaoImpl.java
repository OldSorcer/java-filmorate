package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.user.dao.MpaDao;

import java.util.Collection;

@Component
public class MpaDaoImpl implements MpaDao {
    JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MpaRating getById(int id) {
        String query = "SELECT * FROM mpa_rates WHERE mpa_rate_id = ?;";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> new MpaRating(rs.getInt("mpa_rate_id"), rs.getString("mpa_rate_name")), id);
    }

    @Override
    public Collection<MpaRating> getAll() {
        String query = "SELECT * FROM mpa_rates;";
        return jdbcTemplate.query(query, (rs, rowNum) -> new MpaRating(rs.getInt("mpa_rate_id"), rs.getString("mpa_rate_name")));
    }
}
