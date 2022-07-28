package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.dao.FeedDao;
import ru.yandex.practicum.filmorate.storage.user.dao.impl.UserDaoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
public class FeedDaoImpl implements FeedDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDaoImpl userDaoImpl;

    @Autowired
    public FeedDaoImpl(JdbcTemplate jdbcTemplate, UserDaoImpl userDaoImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDaoImpl = userDaoImpl;
    }

    public void addFeedList(int userId, int entityId, EventType type, Operation operation) {
        final User user = userDaoImpl.getUserById(userId);
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
            Timestamp timestamp = Timestamp.from(Instant.now());
            ps.setLong(5, timestamp.getTime());
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
