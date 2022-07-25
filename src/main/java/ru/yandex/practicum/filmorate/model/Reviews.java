package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {

    private int reviewId;
    private String content;
    private Boolean isPositive;
    private int userId;
    private int filmId;
    private int useful;

}