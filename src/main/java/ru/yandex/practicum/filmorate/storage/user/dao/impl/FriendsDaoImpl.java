package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.dao.impl.FeedDaoImpl;
import ru.yandex.practicum.filmorate.storage.user.dao.FriendsDao;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendsDaoImpl implements FriendsDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDaoImpl userStorage;
    private final FeedDaoImpl feedDaoImpl;

    @Autowired
    public FriendsDaoImpl(JdbcTemplate jdbcTemplate, UserDaoImpl userStorage, FeedDaoImpl feedDaoImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
        this.feedDaoImpl = feedDaoImpl;
    }

    @Override
    public void addFriend(int userId, int targetUserId) {
        User findUser = userStorage.getUserById(targetUserId);
        String sqlQuery = "INSERT INTO friendships (user_id, friend_id, is_confirmed) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, userId, targetUserId, false);
        feedDaoImpl.addFeedList(userId, targetUserId, EventType.FRIEND, Operation.ADD);
    }

    @Override
    public void deleteFriend(int userId, int targetUserId) {
        String sqlQuery = "DELETE FROM friendships WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, targetUserId);
        feedDaoImpl.addFeedList(userId, targetUserId, EventType.FRIEND, Operation.REMOVE);
    }

    @Override
    public List<User> getCommonFriends(int userId, int targetUserId) {
        List<User> userFriends = getFriends(userId);
        List<User> targetUserFriends = getFriends(targetUserId);
        return userFriends.stream().filter(targetUserFriends::contains).collect(Collectors.toList());
    }

    @Override
    public List<User> getFriends(int userId) {
        String sqlQuery = "SELECT * FROM users WHERE user_id IN (SELECT friend_id FROM friendships WHERE user_id = ?)";
        return jdbcTemplate.query(sqlQuery, userStorage::makeUser, userId);
    }
}