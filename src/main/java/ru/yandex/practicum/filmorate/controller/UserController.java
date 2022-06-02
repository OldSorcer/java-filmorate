package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.UserDoesNotExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    Map<Integer, User> userList = new HashMap<>();
    int idCounter = 1;

    @PostMapping
    public User add(@RequestBody User user) throws UserAlreadyExistException, ValidationException{
        if (userList.containsKey(user.getId())) {
            log.warn("Ошибка при добавлении пользователя. Такой пользователь уже существует");
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        } else if (!isValid(user)) {
            log.warn("Ошибка валидации данных пользователя");
            throw new ValidationException("Ошибка валидации данных. Проверьте введенные значения и повторите попытку");
        }
        user.setId(idCounter++);
        userList.put(user.getId(), user);
        log.info("Успешно создан пользователь с id" + user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws UserDoesNotExistException, ValidationException {
        if (!userList.containsKey(user.getId())) {
            log.warn("Ошибка обновления пользователя. Пользователя с таким ID не существует.");
            throw new UserDoesNotExistException("Пользователя с таким ID не существует");
        } else if (!isValid(user)) {
            log.warn("Ошибка валидации данных пользователя");
            throw new ValidationException("Ошибка валидации данных. Проверьте введенные значения и повторите попытку");
        }
        userList.put(user.getId(), user);
        log.info("Успешно обновлен пользователь с ID" + user.getId());
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        return List.copyOf(userList.values());
    }

    public boolean isValid(User user) {
        boolean result = !user.getEmail().isEmpty() &&
                user.getEmail().contains("@") &&
                !user.getLogin().isEmpty() &&
                !user.getLogin().contains(" ") &&
                !user.getBirthday().isAfter(LocalDate.now());
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return result;
    }
}