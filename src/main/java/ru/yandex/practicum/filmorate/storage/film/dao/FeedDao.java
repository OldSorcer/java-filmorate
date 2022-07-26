package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.Operation;

import java.util.List;

public interface FeedDao {
    void addFeedList(int userId, int entityId, EventType type, Operation operation);

    List<Feed> getFeedList(int id);
}
