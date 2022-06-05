package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Integer, User> userList = new HashMap<>();
    private int idCounter = 1;

    @PostMapping
    public User add(@RequestBody User user) {
        if (userList.containsKey(user.getId())) {
            log.warn("Ошибка при добавлении пользователя. Такой пользователь уже существует");
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Такой пользователь уже существует");
        } else if (Validator.isValidUser(user)) {
            user.setId(idCounter++);
            userList.put(user.getId(), user);
            log.info("Успешно создан пользователь с id" + user.getId());
        }
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (!userList.containsKey(user.getId())) {
            log.warn("Ошибка обновления пользователя. Пользователя с таким ID не существует.");
            throw new ValidationException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Пользователя с таким ID не существует");
        } else if (Validator.isValidUser(user)) {
            userList.put(user.getId(), user);
            log.info("Успешно обновлен пользователь с ID" + user.getId());

        }
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        return List.copyOf(userList.values());
    }
}