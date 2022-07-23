package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Reviews;
import ru.yandex.practicum.filmorate.service.ReviewsService;

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
        log.info("Получен POST запрос к эндпоинту /reviews");
        return ResponseEntity.ok().body(reviewsService.addReviews(reviews));
    }

    @PutMapping
    public ResponseEntity<Reviews> updateReviews(@RequestBody Reviews reviews) {
        log.info("Получен PUT запрос к эндпоинту /reviews");
        return ResponseEntity.ok().body(reviewsService.updateReviews(reviews));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Reviews> getReviewsById (@PathVariable int reviewId) {
        log.info("Получен GET запрос к эндпоинту /reviews");
        return ResponseEntity.ok().body(reviewsService.getReviewsById(reviewId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reviews> deleteReviews (@PathVariable int id) {
        log.info("Получен DELETE запрос к эндпоинту /reviews/{id}");
        reviewsService.deleteReviews(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Reviews> addReviewLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен PUT запрос к эндпоинту /reviews/{id}/like/{userId}");
        reviewsService.addReviewLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/dislike/{userId}")
    public ResponseEntity<Reviews> addReviewDislike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен PUT запрос к эндпоинту /reviews/{id}/dislike/{userId}");
        reviewsService.addReviewDislike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Reviews> deleteReviewLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос к эндпоинту /reviews/{id}/like/{userId}");
        reviewsService.deleteReviewLike(id, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public ResponseEntity<Reviews> deleteReviewDislike (@PathVariable int id, @PathVariable int userId) {
        log.info("Получен DELETE запрос к эндпоинту /reviews/{id}/dislike/{userId}");
        reviewsService.deleteReviewDislike(id, userId);
        return ResponseEntity.ok().build();
    }

   @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviewsByFilmId (
            @RequestParam(value = "filmId", defaultValue = "0")  int filmId,
            @RequestParam(value = "count", defaultValue = "10") int count
   ) {
       log.info("Получен GET запрос к эндпоинту /reviews");
         return ResponseEntity.ok().body(reviewsService.getAllReviewsByFilmId(filmId, count));
    }
}