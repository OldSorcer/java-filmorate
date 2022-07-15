package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsDao {
    void addFriend(int userId, int targetUserId);
    void deleteFriend(int userId, int targetUserId);
    List<User> getCommonFriends(int userId, int targetUserId);
    List<User> getFriends(int userId);
}
