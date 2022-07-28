package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.user.dao.FriendsDao;
import ru.yandex.practicum.filmorate.storage.user.dao.UserDao;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final FriendsDao friendsDao;
    private final FilmDao filmDao;

    @Autowired
    public UserService(UserDao userDao, FriendsDao friendsDao, FilmDao filmDao) {
        this.userDao = userDao;
        this.friendsDao = friendsDao;
        this.filmDao = filmDao;
    }

    public User add(User user) {
        return userDao.add(user);
    }

    public User update(User user) {
        return userDao.update(user);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
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
        return friendsDao.getFriends(userDao.getUserById(userId).getId());
    }

    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }

    public List<Film> getRecommendedFilmsList(int id) {
        return filmDao.getRecommendedFilms(id);
    }
}
