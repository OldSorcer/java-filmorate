package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Reviews {

    private int reviewId;
    private String content;
    private Boolean isPositive;
    private int userId;
    private int filmId;
    private int useful;

    public Reviews(int reviewId, String content, boolean isPositive, int userId, int filmId, int useful) {
        this.reviewId = reviewId;
        this.content = content;
        this.isPositive = isPositive;
        this.userId = userId;
        this.filmId = filmId;
        this.useful = useful;
    }
}