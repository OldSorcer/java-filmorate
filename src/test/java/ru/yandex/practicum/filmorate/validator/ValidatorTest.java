package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ValidatorTest {
    private Film film;
    private User user;

    @BeforeEach
    void beforeEach() {
        film = new Film("Film name",
                "Description",
                1,
                LocalDate.now(),
                200,
                Set.of(),
                List.of(new Genre(1, "Комедия")),
                new MpaRating(1, "G"),
                new ArrayList<>());
        user = new User("Login", "Name", "Email@mail.ru", 1, LocalDate.now(), Set.of());
    }

    @Test
    void shouldThrowValidationExceptionIfFilmNameIsEmpty() {
        film.setName("");
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateFilm(film));
    }

    @Test
    void shouldThrowValidationExceptionIfFilmDescriptionLengthIs234() {
        film.setDescription(film.getDescription() + film.getDescription() + film.getDescription());
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateFilm(film));
    }

    @Test
    void shouldThrowValidationExceptionIfFilmReleaseDateIf27December1895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 26));
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateFilm(film));
    }

    @Test
    void shouldReturnValidationExceptionIfFilmDurationIsBelow0() {
        film.setDuration(-1);
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateFilm(film));
    }

    @Test
    void shouldThrowValidationExceptionIfEmailIsEmpty() {
        user.setEmail("");
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateUser(user));
    }

    @Test
    void shouldThrowValidationExceptionIfEmailIncorrect() {
        user.setEmail("Emailmail.ru");
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateUser(user));
    }

    @Test
    void shouldThrowValidationExceptionIfLoginIsEmpty() {
        user.setLogin("");
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateUser(user));
    }

    @Test
    void shouldThrowValidationExceptionIfLoginContainsSpace() {
        user.setLogin("Login login");
        Assertions.assertThrows(ValidationException.class, () -> Validator.validateUser(user));
    }

    @Test
    void shouldSetLoginIfNameIsEmpty() {
        String expectedName = "Login";
        user.setName("");
        Validator.validateUser(user);
        Assertions.assertEquals(expectedName, user.getName());
    }
}