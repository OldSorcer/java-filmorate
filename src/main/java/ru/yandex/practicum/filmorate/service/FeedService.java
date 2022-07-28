package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.storage.film.dao.FeedDao;

import java.util.List;

@Service
public class FeedService {
    private final FeedDao feedDao;

    public FeedService(FeedDao feedDao) {
        this.feedDao = feedDao;
    }

    public List<Feed> getFeedList(int id) {
        return feedDao.getFeedList(id);
    }
}
