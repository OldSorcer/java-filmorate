package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.dao.UserStorage;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.*;
import java.util.List;

@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        Validator.isValidUser(user);
        String sqlQuery = "INSERT INTO USERS (login, user_name, email, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public void delete(User user) {
        String sqlQuery = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    @Override
    public User update(User user) {
        User findUser = getUserById(user.getId());
        String sqlQuery = "UPDATE users SET user_name = ?, login = ?, email = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlQuery, user.getName(), user.getLogin(), user.getEmail(), Date.valueOf(user.getBirthday()), user.getId());
        return user;
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "SELECT * FROM users;";
        List<User> userList = jdbcTemplate.query(sqlQuery, this::makeUser);
        return userList;
    }

    @Override
    public User getUserById(int id) {
        String sqlQuery = "SELECT * FROM users WHERE user_id = ?";
        List<User> user = jdbcTemplate.query(sqlQuery, this::makeUser, id);
        return user.stream().findFirst().orElseThrow(() -> new EntityNotFoundException(HttpStatus.NOT_FOUND, String.format("Пользователь с ID %d не найден", id)));
    }

    public User makeUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setLogin(rs.getString("login"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("user_name"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        user.setId(rs.getInt("user_id"));
        return user;
    }

    public void addFeedList(int userId, int entityId, EventType type, Operation operation) {
        final User user = getUserById(userId);
        String sqlQuery = "INSERT INTO feed (entity_id, operation_name, event_type, user_id, timestamp)" +
                "VALUES (?, ?, ?, ?, ?) ";
        Feed feed = new Feed();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"event_id"});
            ps.setLong(1, entityId);
            ps.setString(2, String.valueOf(operation));
            ps.setString(3, String.valueOf(type));
            ps.setInt(4, user.getId());
            final long timestamp = System.currentTimeMillis() / 1000L;
            ps.setLong(5, timestamp);
            return ps;
        }, keyHolder);
        feed.setEventId(keyHolder.getKey().intValue());
    }

    public List<Feed> getFeedList(int id) {
        String sqlQuery = "SELECT * FROM feed WHERE user_id = ?";
        List<Feed> feedList = jdbcTemplate.query(sqlQuery, this::makeFeed, id);
        return feedList;
    }

    public Feed makeFeed(ResultSet resultSet, int rowNum) throws SQLException {
        Feed feed = new Feed();
        feed.setEventId(resultSet.getInt("event_id"));
        feed.setEntityId(resultSet.getInt("entity_id"));
        final Operation operation = Operation.valueOf(resultSet.getString("operation_name"));
        feed.setOperation(operation);
        final EventType eventType = EventType.valueOf(resultSet.getString("event_type"));
        feed.setEventType(eventType);
        feed.setUserId(resultSet.getInt("user_id"));
        if (operation == Operation.ADD && eventType == EventType.FRIEND) {
            final long timestamp = resultSet.getLong("timestamp");
            feed.setTimestamp(timestamp);
        }
        return feed;
    }
}