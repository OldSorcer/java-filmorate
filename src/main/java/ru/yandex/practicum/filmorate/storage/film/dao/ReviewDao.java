package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.Review;
import java.util.List;

public interface ReviewDao {

    void addReview(Review reviews);
    void updateReview(Review reviews);
    Review getReviewById(int reviewId);
    void deleteReview(int id);
    void addReviewLike(int id, int userId);
    void addReviewDislike(int id, int userId);
    void deleteReviewLike(int id, int userId);
    void deleteReviewDislike(int id, int userId);
    List<Review> getAllReviews();
    List<Review> getReviewsByFilmId(int filmId, int count);
}