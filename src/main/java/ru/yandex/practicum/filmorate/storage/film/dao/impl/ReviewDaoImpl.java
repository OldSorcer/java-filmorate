package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.EventType;
import ru.yandex.practicum.filmorate.model.Operation;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.film.dao.ReviewDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private final JdbcTemplate jdbcTemplate;
    private final FeedDaoImpl feedDaoImpl;

    public ReviewDaoImpl(JdbcTemplate jdbcTemplate, FeedDaoImpl feedDaoImpl) {
        this.jdbcTemplate = jdbcTemplate;
        this.feedDaoImpl = feedDaoImpl;
    }

    @Override
    public void addReview(Review review) {
        String sqlQuery = "INSERT INTO reviews (content, ispositive, user_id, film_id, useful) " +
                          "VALUES (?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"REVIEW_ID"});
            createPreparedStatement(review, stmt);
            return stmt;
        }, keyHolder);
        review.setReviewId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        feedDaoImpl.addFeedList(review.getUserId(), review.getReviewId(), EventType.REVIEW, Operation.ADD);
    }

    private void createPreparedStatement(Review review, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, review.getContent());
        stmt.setBoolean(2, review.getIsPositive());
        stmt.setInt(3, review.getUserId());
        stmt.setInt(4, review.getFilmId());
        stmt.setDouble(5, review.getUseful());
    }

    @Override
    public void updateReview(Review reviews) {
        String sqlQuery = "UPDATE reviews " +
                          "SET content = ?, ispositive = ? " +
                          "WHERE review_id = ?";
        final int updateReviewId = reviews.getReviewId();
        final Review currentReview = getReviewById(updateReviewId);
        final int currentUserId = currentReview.getUserId();
        final int currentFilmId = currentReview.getFilmId();

        if (currentUserId != reviews.getUserId() || currentFilmId != reviews.getFilmId()) {
            reviews.setUserId(currentUserId);
            reviews.setFilmId(currentFilmId);
        }
        jdbcTemplate.update(sqlQuery
                , reviews.getContent()
                , reviews.getIsPositive()
                , reviews.getReviewId());
        feedDaoImpl.addFeedList(reviews.getUserId(), reviews.getReviewId(), EventType.REVIEW, Operation.UPDATE);
    }

    @Override
    public Review getReviewById(int id) {
        String sqlQuery = "SELECT review_id, content, ispositive, user_id, film_id, useful " +
                          "FROM reviews " +
                          "WHERE review_id = ?";
        List<Review> reviewsList = jdbcTemplate.query(sqlQuery, this::makeReview, id);
        return reviewsList.stream().findFirst().orElseThrow(() ->
                new EntityNotFoundException(HttpStatus.NOT_FOUND,
                        String.format("Отзыв с ID %d не найден", id)));
    }

    @Override
    public void deleteReview(int id) {
        final Review reviews = getReviewById(id);
        feedDaoImpl.addFeedList(reviews.getUserId(), id, EventType.REVIEW, Operation.REMOVE);
        String sqlQuery = "DELETE FROM reviews " +
                          "WHERE review_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void addReviewLike(int id, int userId) {
        String sqlQuery = "INSERT INTO reviews_like (review_id, user_id) " +
                          "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, id, userId);
        addToRating(id);
    }

    @Override
    public void addReviewDislike(int id, int userId) {
        String sqlQuery = "INSERT INTO reviews_dislike (review_id, user_id) " +
                          "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, id, userId);
        takeAwayFromRating(id);
    }

    @Override
    public void deleteReviewLike(int id, int userId) {
        String sqlQuery = "DELETE FROM reviews_like " +
                          "WHERE review_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, id);
        takeAwayFromRating(id);
    }

    @Override
    public void deleteReviewDislike(int id, int userId) {
        String sqlQuery = "DELETE FROM reviews_dislike " +
                          "WHERE review_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, id);
        addToRating(id);
    }

    @Override
    public List<Review> getAllReviews() {
        String sqlQuery = "SELECT * " +
                          "FROM reviews " +
                          "GROUP BY review_id " +
                          "ORDER BY useful DESC ";
        return jdbcTemplate.query(sqlQuery, this::makeReview);
    }

    @Override
    public List<Review> getReviewsByFilmId(int filmId, int count) {
        String sqlQuery = "SELECT * " +
                          "FROM reviews " +
                          "WHERE film_id = ? " +
                          "GROUP BY review_id " +
                          "ORDER BY useful DESC " +
                          "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeReview, filmId, count);
    }

    private void addToRating(int id) {
        String sqlQuery = "UPDATE reviews SET useful = useful + 1 " +
                          "WHERE review_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private void takeAwayFromRating(int id) {
        String sqlQuery = "UPDATE reviews SET useful = useful  - 1 " +
                          "WHERE review_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    private Review makeReview(ResultSet rs, int rowNum) throws SQLException {
        return new Review(
                rs.getInt("REVIEW_ID"),
                rs.getString("CONTENT"),
                rs.getBoolean("isPOSITIVE"),
                rs.getInt("USER_ID"),
                rs.getInt("FILM_ID"),
                rs.getInt("USEFUL")
        );
    }
}