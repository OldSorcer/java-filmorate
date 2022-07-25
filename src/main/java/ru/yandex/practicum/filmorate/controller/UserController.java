package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Feed;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController() {

    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User add(@RequestBody User user) {
        log.info("Получен POST запрос к эндпоинту /users");
        return userService.add(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        log.info("Получен PUT запрос к эндпоинту /users");
        return userService.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Получен GET запрос к эндпоинту /users");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен GET запрос к эндпоинту /users/{}", id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id,
                          @PathVariable int friendId) {
        log.info("Получен PUT запрос к эндпоинту /users/{}/friends{}", id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFromFriend(@PathVariable int id,
                                 @PathVariable int friendId) {
        log.info("Получен DELETE запрос к экндпоинту /users/{}/friends/{}", id, friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriendList(@PathVariable int id) {
        log.info("Получен GET запрос к эндпоинту /users/{}/friends", id);
        return userService.getFriendList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable int id,
                                             @PathVariable int otherId) {
        log.info("Получен GET запрос к эндпоинту /users/{}/friends/common/{}", id, otherId);
        return userService.getCommonFriendsId(id, otherId);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        log.info("Получен DELETE запрос к эндпоинту /users/{}", id);
        userService.deleteUserById(id);
    }

    @GetMapping("/{id}/feed")
    public Collection<Feed> getFeedList(@PathVariable int id) {
        log.info("Получаем GET запрос к эндпойнту /users/{}/feed", id);
        return userService.getFeedList(id);
    }
}