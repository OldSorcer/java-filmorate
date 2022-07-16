package ru.yandex.practicum.filmorate.storage.film.dao;

import java.util.List;

public interface LikesDao {
    void addLike(int userId, int filmId);
    void deleteLike(int userId, int filmId);
    List<Integer> getLikes(int filmId);
}
