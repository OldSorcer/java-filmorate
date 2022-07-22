package ru.yandex.practicum.filmorate.storage.film.dao;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Reviews;

import java.util.List;

@Component
public interface ReviewsDao {

    void addReviews(Reviews reviews);

    void updateReviews(Reviews reviews);

    Reviews getReviewById(int reviewId);


    void deleteReviews(int id);

    void addReviewLike(int id, int userId);

    void addReviewDislike(int id, int userId);

    void deleteReviewLike(int id, int userId);

    void deleteReviewDislike(int id, int userId);

    List<Reviews> getAllReviewsByFilmId(int filmId, int count);
}
