package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

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
    void shouldReturnUserIfAdded() {
        Assertions.assertEquals(user, userController.add(user));
    }

    @Test
    void shouldThrowValidationExceptionIfUserAlreadyExist() {
        userController.add(user);
        Assertions.assertThrows(ValidationException.class, () -> userController.add(user));
    }

    @Test
    void shouldReturnUserIfUpdated() {
        userController.add(user);
        user.setName("New name");
        Assertions.assertEquals(user, userController.update(user));
    }

    @Test
    void shouldThrowValidationExceptionIfUserDoesNotExist() {
        userController.add(user);
        user.setId(2);
        Assertions.assertThrows(ValidationException.class, () -> userController.update(user));
    }

    @Test
    void shouldReturnUserList() {
        List<User> expectedList = List.of(user);

        userController.add(user);
        Assertions.assertEquals(expectedList, userController.getAll());
    }
}