package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.dao.LikesDao;
import ru.yandex.practicum.filmorate.storage.user.dao.impl.UserDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LikesDaoImpl implements LikesDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDaoImpl userDaoImpl;
    private final FeedDaoImpl feedDaoImpl;

    @Autowired
    public LikesDaoImpl(JdbcTemplate jdbcTemplate, UserDaoImpl userDaoImpl, FeedDaoImpl feedDaoImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDaoImpl = userDaoImpl;
        this.feedDaoImpl = feedDaoImpl;
    }

    @Override
    public void addLike(int userId, int filmId) {
        User user = userDaoImpl.getUserById(userId);
        String sqlQuery = "INSERT INTO films_likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
        feedDaoImpl.addFeedList(userId, filmId, EventType.LIKE, Operation.ADD);
    }

    @Override
    public void deleteLike(int userId, int filmId) {
        User user = userDaoImpl.getUserById(userId);
        String sqlQuery = "DELETE FROM films_likes WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId(), filmId);
        feedDaoImpl.addFeedList(userId, filmId, EventType.LIKE, Operation.REMOVE);
    }

    @Override
    public List<Integer> getLikes(int filmId) {
        String sqlQuery = "SELECT user_id FROM films_likes WHERE film_id = ?";
        return jdbcTemplate.query(sqlQuery, this::makeLike, filmId);
    }

    private Integer makeLike(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("user_id");
    }
}