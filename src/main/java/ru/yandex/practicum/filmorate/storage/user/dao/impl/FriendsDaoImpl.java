package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.dao.FriendsDao;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendsDaoImpl implements FriendsDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userStorage;

    @Autowired
    public FriendsDaoImpl(JdbcTemplate jdbcTemplate, UserDbStorage userStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userStorage = userStorage;
    }

    @Override
    public void addFriend(int userId, int targetUserId) {
        User findUser = userStorage.getUserById(targetUserId);
        String sqlQuery = "INSERT INTO friendships (user_id, friend_id, is_confirmed) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, userId, targetUserId, false);
    }

    @Override
    public void deleteFriend(int userId, int targetUserId) {
        String sqlQuery = "DELETE FROM friendships WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, targetUserId);
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
        List<User> userFriends = jdbcTemplate.query(sqlQuery, userStorage::makeUser, userId);
        return userFriends;
    }
}