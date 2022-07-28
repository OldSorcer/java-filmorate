package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Feed {
    private int eventId;            //     "eventId": 1234, // primary key
    private int userId;             //     "userId": 123,
    private int entityId;           //     "entityId": 1234 // идентификатор сущности, с которой произошло событие
    private EventType eventType;    //     "eventType": "LIKE", // одно из значениий LIKE, REVIEW или FRIEND
    private Operation operation;    //     "operation": "REMOVE", // одно из значениий REMOVE, ADD, UPDATE
    private Long timestamp;         //     "timestamp": 123344556,
}