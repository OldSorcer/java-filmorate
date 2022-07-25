package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.Reviews;
import java.util.List;

public interface ReviewsDao {

    void addReviews(Reviews reviews);
    void updateReviews(Reviews reviews);
    Reviews getReviewById(int reviewId);
    void deleteReviews(int id);
    void addReviewLike(int id, int userId);
    void addReviewDislike(int id, int userId);
    void deleteReviewLike(int id, int userId);
    void deleteReviewDislike(int id, int userId);
    List<Reviews> getAllReviews();
    List<Reviews> getReviewsByFilmId(int filmId, int count);
}