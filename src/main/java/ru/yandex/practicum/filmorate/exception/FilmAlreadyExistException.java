package ru.yandex.practicum.filmorate.exception;

import java.io.InvalidObjectException;

public class FilmAlreadyExistException extends InvalidObjectException {
    public FilmAlreadyExistException(String message) {
        super(message);
    }
}
