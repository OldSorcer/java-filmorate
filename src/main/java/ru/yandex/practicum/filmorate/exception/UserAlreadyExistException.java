package ru.yandex.practicum.filmorate.exception;

import java.io.InvalidObjectException;

public class UserAlreadyExistException extends InvalidObjectException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
