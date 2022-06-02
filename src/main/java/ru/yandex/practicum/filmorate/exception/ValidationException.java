package ru.yandex.practicum.filmorate.exception;

import java.io.InvalidObjectException;

public class ValidationException extends InvalidObjectException {
    public ValidationException(String message) {
        super(message);
    }
}
