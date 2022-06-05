package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

class ValidatorTest {
    private Film film;
    private User user;

    @BeforeEach
    void beforeEach() {
        film = new Film("Name of film",
                "Description of a very interesting movie with kittens, pink ponies and unicorns",
                1,
                LocalDate.now(),
                120);
        user = new User("Login", "Name", "Email@mail.ru", 1, LocalDate.now());
    }

    @Test
    void shouldThrowValidationExceptionIfFilmNameIsEmpty() {
        film.setName("");
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidFilm(film));
    }

    @Test
    void shouldThrowValidationExceptionIfFilmDescriptionLengthIs234() {
        film.setDescription(film.getDescription() + film.getDescription() + film.getDescription());
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidFilm(film));
    }

    @Test
    void shouldThrowValidationExceptionIfFilmReleaseDateIf27December1895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 26));
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidFilm(film));
    }

    @Test
    void shouldReturnValidationExceptionIfFilmDurationIsBelow0() {
        film.setDuration(-1);
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidFilm(film));
    }

    @Test
    void shouldReturnTrueIfFilmIsValid() {
        Assertions.assertTrue(Validator.isValidFilm(film));
    }

    @Test
    void shouldThrowValidationExceptionIfEmailIsEmpty() {
        user.setEmail("");
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidUser(user));
    }

    @Test
    void shouldThrowValidationExceptionIfEmailIncorrect() {
        user.setEmail("Emailmail.ru");
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidUser(user));
    }

    @Test
    void shouldThrowValidationExceptionIfLoginIsEmpty() {
        user.setLogin("");
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidUser(user));
    }

    @Test
    void shouldThrowValidationExceptionIfLoginContainsSpace() {
        user.setLogin("Login login");
        Assertions.assertThrows(ValidationException.class, () -> Validator.isValidUser(user));
    }

    @Test
    void shouldSetLoginIfNameIsEmpty() {
        String expectedName = "Login";

        user.setName("");
        Validator.isValidUser(user);
        Assertions.assertEquals(expectedName, user.getName());
    }

    @Test
    void souldReturnTrueIfUserIsValid() {
        Assertions.assertTrue(Validator.isValidUser(user));
    }
}