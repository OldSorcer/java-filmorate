package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.film.dao.ReviewsDao;
import ru.yandex.practicum.filmorate.validator.Validator;
import java.util.List;

@Service
@Slf4j
public class ReviewsService {

    private final ReviewsDao reviewsDao;
    private final UserService userService;
    private  final FilmService filmService;

    @Autowired
    public ReviewsService(ReviewsDao reviewsDao, UserService userService, FilmService filmService) {
        this.reviewsDao = reviewsDao;
        this.userService = userService;
        this.filmService = filmService;
    }

    public Review addReview(Review review) {
        Validator.validateReview(review);
        userService.getUserById(review.getUserId());
        filmService.getFilmById(review.getFilmId());
        reviewsDao.addReview(review);
        return review;
    }

    public Review updateReview(Review review) {
        getReviewById(review.getReviewId());
        reviewsDao.updateReview(review);
        return getReviewById(review.getReviewId());
    }

    public Review getReviewById(int reviewId) {
        return reviewsDao.getReviewById(reviewId);
    }

    public void deleteReview(int id) {
        getReviewById(id);
        reviewsDao.deleteReview(id);
    }

    public void addReviewLike(int id, int userId) {
        getReviewById(id);
        userService.getUserById(userId);
        reviewsDao.addReviewLike(id, userId);
    }

    public void addReviewDislike(int id, int userId) {
        getReviewById(id);
        userService.getUserById(userId);
        reviewsDao.addReviewDislike(id, userId);
    }

    public void deleteReviewLike(int id, int userId) {
        getReviewById(id);
        userService.getUserById(userId);
        reviewsDao.deleteReviewLike(id, userId);
    }

    public void deleteReviewDislike(int id, int userId) {
        getReviewById(id);
        userService.getUserById(userId);
        reviewsDao.deleteReviewDislike(id, userId);
    }

    public List<Review> getAllReviewsByFilmId(int filmId, int count) {
        if (filmId == 0) {
            return reviewsDao.getAllReviews();
        } else {
            return reviewsDao.getReviewsByFilmId(filmId, count);
        }
    }
}