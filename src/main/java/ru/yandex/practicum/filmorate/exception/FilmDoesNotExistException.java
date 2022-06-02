package ru.yandex.practicum.filmorate.exception;

import java.io.InvalidObjectException;
import java.time.Duration;

public class FilmDoesNotExistException extends InvalidObjectException {
    public FilmDoesNotExistException (String message) {
        super(message);
    }
}