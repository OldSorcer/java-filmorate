package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.List;

@Controller
@RequestMapping("/reviews")
@Slf4j
public class ReviewController {
    private final ReviewService reviewsService;

    @Autowired
    public ReviewController(ReviewService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @PostMapping
    public ResponseEntity<Review> addReview (@RequestBody Review reviews) {
        log.info("Получен POST запрос к эндпоинту /reviews");
        return ResponseEntity.ok().body(reviewsService.addReview(reviews));
    }

    @PutMapping
    public ResponseEntity<Review> updateReview (@RequestBody Review review) {
        log.info("Получен PUT запрос к эндпоинту /reviews");
        return ResponseEntity.ok().body(reviewsService.updateReview(review));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById (@PathVariable int reviewId) {
        log.info("Получен GET запрос к эндпоинту /reviews");
        return ResponseEntity.ok().body(reviewsService.getReviewById(reviewId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable int id) {
        log.info("Получен DELETE запрос к эндпоинту /reviews/{id}");
        reviewsService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Review> addReviewLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен PUT запрос к эндпоинту /reviews/{id}/like/{userId}");
        reviewsService.addReviewLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/dislike/{userId}")
    public ResponseEntity<Review> addReviewDislike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен PUT запрос к эндпоинту /reviews/{id}/dislike/{userId}");
        reviewsService.addReviewDislike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Review> deleteReviewLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос к эндпоинту /reviews/{id}/like/{userId}");
        reviewsService.deleteReviewLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public ResponseEntity<Review> deleteReviewDislike (@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос к эндпоинту /reviews/{id}/dislike/{userId}");
        reviewsService.deleteReviewDislike(id, userId);
        return ResponseEntity.ok().build();
    }

   @GetMapping
    public ResponseEntity<List<Review>> getAllReviewsByFilmId (
            @RequestParam(value = "filmId", defaultValue = "0")  int filmId,
            @RequestParam(value = "count", defaultValue = "10") int count
   ) {
       log.info("Получен GET запрос к эндпоинту /reviews");
         return ResponseEntity.ok().body(reviewsService.getAllReviewsByFilmId(filmId, count));
    }
}