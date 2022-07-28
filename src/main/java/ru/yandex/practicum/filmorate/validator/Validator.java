package ru.yandex.practicum.filmorate.validator;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    private final static int MAX_DESCRIPTION_LENGTH = 200;
    private final static LocalDate LATEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public static void validateFilm(Film film) {
        validateFilmName(film.getName());
        validateDescription(film.getDescription());
        validateReleaseDate(film.getReleaseDate());
        validateDuration(film.getDuration());
    }

    public static void validateUser(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        validateEmail(user.getEmail());
        validateBirthDay(user.getBirthday());
        validateLogin(user.getLogin());
    }

    public static void validateDirector(Director director) {
        if (director.getName().isBlank()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Имя режиссера не должно быть пустым");
        }
    }

    public static void validateReview(Review review) {
        validateReviewContent(review.getContent());
        validateIsPositive(review.getIsPositive());
        validateUserId(review.getUserId());
        validateFilmId(review.getFilmId());
    }

    private static void validateFilmName(String name) {
        if (name.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Название фильма не должно быть пустым.");
        }
    }

    private static void validateDescription(String description) {
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Длина описания не должна превышать " + MAX_DESCRIPTION_LENGTH + " символов");
        }
    }

    private static void validateReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(LATEST_RELEASE_DATE)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Дата релиза должна быть не раньше " + LATEST_RELEASE_DATE);
        }
    }

    private static void validateDuration(int duration) {
        if (duration < 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Продолжительность фильма должна быть положительной");
        }
    }

    private static void validateEmail(String email) {
        if (!email.contains("@")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Введен некорректный EMAIL адрес");
        }
    }

    private static void validateLogin(String login) {
        if (login.isEmpty() || login.contains(" ")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Логин не должен быть пустым или содержать пробелы");
        }
    }

    private static void validateBirthDay(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Дата рождения не должна быть позже " + LocalDate.now());
        }
    }

    private static void validateReviewContent(String content) {
        if (content.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Отзыв не должен быть пустым.");
        }
    }

    private static void validateIsPositive(Boolean isPositive) {
        if (isPositive == null) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Тип отзыва не должен быть пустым.");
        }
    }

    private static void validateUserId(int userId ) {
        if (userId == 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "ID пользователя не должно быть пустым.");
        }
    }

    private static void validateFilmId(int filmId ) {
        if (filmId == 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "ID фильма не должно быть пустым.");
        }
    }
}