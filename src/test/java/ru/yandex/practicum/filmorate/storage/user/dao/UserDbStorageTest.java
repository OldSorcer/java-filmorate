package ru.yandex.practicum.filmorate.storage.user.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.dao.impl.UserDbStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private User user = new User("Login", "Name", "email@mail.ru", 1, LocalDate.now());

    @Test
    public void addUser() {
        userDbStorage.add(user);
        Assertions.assertEquals(user, userDbStorage.getUserById(1));
    }

    @Test
    public void findUserById1() {
        Assertions.assertEquals(user, userDbStorage.getUserById(1));
    }

    @Test
    public void updateUserEmail() {
        String newEmail = "email@storage.ru";
        user.setEmail(newEmail);
        userDbStorage.update(user);
        Assertions.assertEquals(newEmail, userDbStorage.getUserById(1).getEmail());
    }

    @Test
    public void updateNotExistsUser() {
        User notExistUser = new User("Login", "Name", "email@mail.ru", 2, LocalDate.now());
        Assertions.assertThrows(EntityNotFoundException.class, () -> userDbStorage.update(notExistUser));
    }

    @Test
    public void getAllUsers() {
        Assertions.assertEquals(1, userDbStorage.getAll().size());
    }

    @Test
    public void deleteUser() {
        userDbStorage.delete(user);
        Assertions.assertTrue(userDbStorage.getAll().isEmpty());
    }
}