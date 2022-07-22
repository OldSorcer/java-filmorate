package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Reviews;
import ru.yandex.practicum.filmorate.service.ReviewsService;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.List;

@Controller
@RequestMapping("/reviews")
@Slf4j
public class ReviewsController {
    private final ReviewsService reviewsService;

    @Autowired
    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @PostMapping
    public ResponseEntity<Reviews> addReviews(@RequestBody Reviews reviews) {
        Validator.isValidReview(reviews);
        return ResponseEntity.ok().body(reviewsService.addReviews(reviews));
    }

    @PutMapping
    public ResponseEntity<Reviews> updateReviews(@RequestBody Reviews reviews) {
        return ResponseEntity.ok().body(reviewsService.updateReviews(reviews));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Reviews> getReviewsById (@PathVariable int reviewId) {
        return ResponseEntity.ok().body(reviewsService.getReviewsById(reviewId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Reviews> deleteReviews (@PathVariable int id) {
        reviewsService.deleteReviews(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Reviews> addReviewLike(@PathVariable int id, @PathVariable int userId) {
        reviewsService.addReviewLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/dislike/{userId}")
    public ResponseEntity<Reviews> addReviewDislike(@PathVariable int id, @PathVariable int userId) {
        reviewsService.addReviewDislike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Reviews> deleteReviewLike(@PathVariable int id, @PathVariable int userId) {
        reviewsService.deleteReviewLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public ResponseEntity<Reviews> deleteReviewDislike (@PathVariable int id, @PathVariable int userId) {
        reviewsService.deleteReviewDislike(id, userId);
        return ResponseEntity.ok().build();
    }

   @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviewsByFilmId (
            @RequestParam(value = "filmId", defaultValue = "0")  int filmId,
            @RequestParam(value = "count", defaultValue = "10") int count
   ) {
         return ResponseEntity.ok().body(reviewsService.getAllReviewsByFilmId(filmId, count));
    }
}
