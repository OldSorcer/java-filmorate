package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Reviews;
import ru.yandex.practicum.filmorate.storage.film.dao.ReviewsDao;
<<<<<<< HEAD
import ru.yandex.practicum.filmorate.validator.Validator;

=======
>>>>>>> origin/add-reviews
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

    public Reviews addReviews(Reviews reviews) {
<<<<<<< HEAD
        Validator.isValidReview(reviews);
=======
>>>>>>> origin/add-reviews
        userService.getUserById(reviews.getUserId());
        filmService.getFilmById(reviews.getFilmId());
        reviewsDao.addReviews(reviews);
        return reviews;
    }

    public Reviews updateReviews(Reviews reviews) {
        getReviewsById(reviews.getReviewId());
        reviewsDao.updateReviews(reviews);
        return getReviewsById(reviews.getReviewId());
    }

    public Reviews getReviewsById(int reviewId) {
        return reviewsDao.getReviewById(reviewId);
    }

    public void deleteReviews(int id) {
        getReviewsById(id);
        reviewsDao.deleteReviews(id);
    }

    public void addReviewLike(int id, int userId) {
        getReviewsById(id);
        userService.getUserById(userId);
        reviewsDao.addReviewLike(id, userId);
    }

    public void addReviewDislike(int id, int userId) {
        getReviewsById(id);
        userService.getUserById(userId);
        reviewsDao.addReviewDislike(id, userId);
    }

    public void deleteReviewLike(int id, int userId) {
        getReviewsById(id);
        userService.getUserById(userId);
        reviewsDao.deleteReviewLike(id, userId);
    }

    public void deleteReviewDislike(int id, int userId) {
        getReviewsById(id);
        userService.getUserById(userId);
        reviewsDao.deleteReviewDislike(id, userId);
    }

    public List<Reviews> getAllReviewsByFilmId(int filmId, int count) {
<<<<<<< HEAD
        if (filmId == 0) {
            return reviewsDao.getAllReviews();
        } else {
            return reviewsDao.getReviewsByFilmId(filmId, count);
        }

>>>>>>> origin/add-reviews
    }
}
