package ru.yandex.practicum.filmorate.validator;

import org.springframework.http.HttpStatus;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Reviews;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    private final static int MAX_DESCRIPTION_LENGTH = 200;
    private final static LocalDate LATEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public static boolean isValidFilm(Film film) {
        return isValidFilmName(film.getName()) &&
                isValidDescription(film.getDescription()) &&
                isValidReleaseDate(film.getReleaseDate()) &&
                isValidDuration(film.getDuration());
    }

    public static boolean isValidUser(User user) {
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return isValidLogin(user.getLogin()) &&
                isValidEmail(user.getEmail()) &&
                isValidBirthDay(user.getBirthday());
    }

    public static boolean isValidDirector(Director director) {
        if (director.getName().isBlank()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Имя режиссера не должно быть пустым");
        }
        return true;
    }

    public static boolean isValidReview(Reviews review) {
        return isValidReviewContent(review.getContent()) &&
                isValidIsPositive(review.getIsPositive()) &&
                isValidUserId(review.getUserId()) &&
                isValidFilmId(review.getFilmId());
    }

    private static boolean isValidFilmName(String name) {
        if (name.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Название фильма не должно быть пустым.");
        } else {
            return true;
        }
    }

    private static boolean isValidDescription(String description) {
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Длина описания не должна превышать " + MAX_DESCRIPTION_LENGTH + " символов");
        } else {
            return true;
        }
    }

    private static boolean isValidReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isBefore(LATEST_RELEASE_DATE)) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Дата релиза должна быть не раньше " + LATEST_RELEASE_DATE);
        } else {
            return true;
        }
    }

    private static boolean isValidDuration(int duration) {
        if (duration < 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Продолжительность фильма должна быть положительной");
        } else {
            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        if (!email.contains("@")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Введен некорректный EMAIL адрес");
        } else {
            return true;
        }
    }

    private static boolean isValidLogin(String login) {
        if (login.isEmpty() || login.contains(" ")) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Логин не должен быть пустым или содержать пробелы");
        } else {
            return true;
        }
    }

    private static boolean isValidBirthDay(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Дата рождения не должна быть позже " + LocalDate.now());
        } else {
            return true;
        }
    }

    private static boolean isValidReviewContent(String content) {
        if (content.isEmpty()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Отзыв не должен быть пустым.");
        } else {
            return true;
        }
    }

    private static boolean isValidIsPositive(Boolean isPositive) {
        if (isPositive == null) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Тип отзыва не должен быть пустым.");
        } else {
            return true;
        }
    }

    private static boolean isValidUserId(int userId ) {
        if (userId == 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "ID пользователя не должно быть пустым.");
        } else {
            return true;
        }
    }

    private static boolean isValidFilmId(int filmId ) {
        if (filmId == 0) {
            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "ID фильма не должно быть пустым.");
        } else {
            return true;
        }
    }
}