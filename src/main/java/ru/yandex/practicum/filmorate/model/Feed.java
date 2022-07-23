package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Feed {
    long timestamp;         //     "timestamp": 123344556,
    int userId;             //     "userId": 123,
    EventType eventType;    //     "eventType": "LIKE", // одно из значениий LIKE, REVIEW или FRIEND
    Operation operation;    //     "operation": "REMOVE", // одно из значениий REMOVE, ADD, UPDATE
    int eventId;            //     "eventId": 1234, // primary key
    int entityId;           //     "entityId": 1234 // идентификатор сущности, с которой произошло событие
}