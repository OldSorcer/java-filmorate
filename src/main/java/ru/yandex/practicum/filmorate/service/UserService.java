package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
        return userStorage.add(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public void addFriend(int userId, int targetUserId) {
        User user = userStorage.getUserById(userId);
        User targetUser = userStorage.getUserById(targetUserId);
        user.getFriendsList().add(targetUser.getId());
        targetUser.getFriendsList().add(user.getId());
    }

    public void deleteFriend(int userId, int targetUserId) {
        User user = userStorage.getUserById(userId);
        User targetUser = userStorage.getUserById(targetUserId);
        user.getFriendsList().remove(targetUserId);
        targetUser.getFriendsList().remove(userId);
    }

    public List<User> getCommonFriendsId(int userId, int targetUserId) {
        List<User> userFriends = getFriendList(userId);
        List<User> targetUserFriends = getFriendList(targetUserId);
        return userFriends.stream()
                .filter(targetUserFriends::contains)
                .collect(Collectors.toList());
    }

    public List<User> getFriendList(int userId) {
        return userStorage.getUserById(userId).getFriendsList()
                .stream()
                .map(f -> userStorage.getUserById(f))
                .collect(Collectors.toList());
    }
}