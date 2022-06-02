package ru.yandex.practicum.filmorate.exception;

import java.io.InvalidObjectException;

public class UserDoesNotExistException extends InvalidObjectException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
