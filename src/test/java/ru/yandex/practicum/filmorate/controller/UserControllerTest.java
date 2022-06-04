package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
class UserControllerTest {
    private UserController userController;
    private User user;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
        user = new User("Login", "Name", "Email@mail.ru", 1, LocalDate.now());
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
    void shouldSetLoginIfNameIsEmpty() {
        String expectedName = "Login";

        user.setName("");
        userController.add(user);
        Assertions.assertEquals(expectedName, userController.getAll().get(0).getName());
    }
}