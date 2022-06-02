package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.FilmDoesNotExistException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {
	FilmController filmController;
	UserController userController;
	Film film;
	User user;

	@BeforeEach
	void beforeEach() {
		filmController = new FilmController();
		userController = new UserController();
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
		Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
	}

	@Test
	void shouldThrowValidationExceptionIfFilmDescriptionLengthIs234() {
        film.setDescription(film.getDescription() + film.getDescription() + film.getDescription());
		Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
	}

	@Test
	void shouldThrowValidationExceptionIfFilmReleaseDateIf27December1895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 26));
		Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
	}

	@Test
	void shouldReturnValidationExceptionIfFilmDurationIsBelow0() {
		film.setDuration(-1);
		Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
	}

	@Test
	void filmShouldBeAdded() throws ValidationException, FilmAlreadyExistException {
		filmController.add(film);
		Assertions.assertAll(() -> Assertions.assertEquals(1, filmController.getAll().size()),
				() -> Assertions.assertEquals(film, filmController.getAll().get(0)));
	}

	@Test
	void shouldThrowValidationExceptionIfEmailIsEmpty() {
		user.setEmail("");
		Assertions.assertThrows(ValidationException.class, () -> userController.add(user));
	}

	@Test
	void shouldThrowValidationExceptionIfEmailIncorrect() {
		user.setEmail("Emailmail.ru");
		Assertions.assertThrows(ValidationException.class, () -> userController.add(user));
	}

	@Test
	void shouldThrowValidationExceptionIfLoginIsEmpty() {
		user.setLogin("");
		Assertions.assertThrows(ValidationException.class, () -> userController.add(user));
	}

	@Test
	void shouldThrowValidationExceptionIfLoginContainsSpace() {
		user.setLogin("Login login");
		Assertions.assertThrows(ValidationException.class, () -> userController.add(user));
	}

	@Test
	void shouldSetLoginIfNameIsEmpty() throws ValidationException, UserAlreadyExistException {
		String expectedName = "Login";

		user.setName("");
		userController.add(user);
		Assertions.assertEquals(expectedName, userController.getAll().get(0).getName());
	}
}