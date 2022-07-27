package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class Feed {
    int eventId;            //     "eventId": 1234, // primary key
    int userId;             //     "userId": 123,
    int entityId;           //     "entityId": 1234 // идентификатор сущности, с которой произошло событие
    EventType eventType;    //     "eventType": "LIKE", // одно из значениий LIKE, REVIEW или FRIEND
    Operation operation;    //     "operation": "REMOVE", // одно из значениий REMOVE, ADD, UPDATE
    Long timestamp;         //     "timestamp": 123344556,
}