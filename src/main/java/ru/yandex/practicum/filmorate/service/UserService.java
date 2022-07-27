package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.dao.FriendsDao;
import ru.yandex.practicum.filmorate.storage.user.dao.UserStorage;

import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;
    private final FriendsDao friendsDao;
    private final FilmStorage filmStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendsDao friendsDao, FilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.friendsDao = friendsDao;
        this.filmStorage = filmStorage;
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
        friendsDao.addFriend(userId, targetUserId);
    }

    public void deleteFriend(int userId, int targetUserId) {
        friendsDao.deleteFriend(userId, targetUserId);
    }

    public List<User> getCommonFriendsId(int userId, int targetUserId) {
        return friendsDao.getCommonFriends(userId, targetUserId);
    }

    public List<User> getFriendList(int userId) {
        return friendsDao.getFriends(userStorage.getUserById(userId).getId());
    }

    public void deleteUserById(int id) {
        userStorage.deleteUserById(id);
    }

    public List<Film> getRecommendedFilmsList(int id) {
        return filmStorage.getRecommendedFilms(id);
    }
}
