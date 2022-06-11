package ru.yandex.practicum.filmorate.service;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userId, int targetUserId) {
        User user = userStorage.getAll().get(userId);
        User targetUser = userStorage.getAll().get(targetUserId);
        user.getFriendsList().add(targetUser.getId());
        targetUser.getFriendsList().add(user.getId());
    }

    public void deleteFriend(int userId, int targetUserId) {
        User user = userStorage.getAll().get(userId);
        User targetUser = userStorage.getAll().get(targetUserId);
        user.getFriendsList().remove(targetUserId);
        targetUser.getFriendsList().remove(userId);
    }

    public List<User> getCommonFriendsId(int userId, int targetUserId) {
        User user = userStorage.getAll().get(userId);
        User targetUser = userStorage.getAll().get(targetUserId);
        return user.getFriendsList().stream()
                .filter(f -> targetUser.getFriendsList().contains(f))
                .map(userStorage.getAll()::get)
                .collect(Collectors.toList());
    }
}