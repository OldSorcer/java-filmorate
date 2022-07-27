CREATE TABLE IF NOT EXISTS USERS (
                                     USER_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     LOGIN VARCHAR UNIQUE NOT NULL,
                                     USER_NAME VARCHAR UNIQUE NOT NULL,
                                     EMAIL VARCHAR NOT NULL,
                                     BIRTHDAY DATE
);

CREATE TABLE IF NOT EXISTS FRIENDSHIPS(
                                           USER_ID INTEGER REFERENCES USERS(USER_ID) ON DELETE CASCADE,
                                           FRIEND_ID INTEGER REFERENCES USERS(USER_ID) ON DELETE CASCADE,
                                           IS_CONFIRMED BOOLEAN,
                                           PRIMARY KEY (USER_ID, FRIEND_ID)
);

CREATE TABLE IF NOT EXISTS MPA_RATES (
                                   MPA_RATE_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                                   MPA_RATE_NAME varchar
);

CREATE TABLE IF NOT EXISTS FILMS (
                                     FILM_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                                     FILM_NAME varchar,
                                     DESCRIPTION varchar(200),
                                     RELEASE_DATE DATE,
                                     DURATION INTEGER,
                                     MPA_RATE_ID INTEGER REFERENCES MPA_RATES(MPA_RATE_ID)
);

CREATE TABLE IF NOT EXISTS GENRES (
                                      GENRE_ID INTEGER PRIMARY KEY,
                                      NAME varchar
);

CREATE TABLE IF NOT EXISTS FILMS_LIKES (
                                          FILM_ID INTEGER REFERENCES FILMS(FILM_ID) ON DELETE CASCADE,
                                          USER_ID INTEGER REFERENCES USERS(USER_ID) ON DELETE CASCADE,
                                          PRIMARY KEY (FILM_ID, USER_ID)
);

CREATE TABLE IF NOT EXISTS FILMS_GENRES (
                                           FILM_ID int REFERENCES FILMS(FILM_ID) ON DELETE CASCADE,
                                           GENRE_ID int REFERENCES GENRES(GENRE_ID) ON DELETE CASCADE,
                                           PRIMARY KEY (FILM_ID, GENRE_ID)
);

CREATE TABLE IF NOT EXISTS REVIEWS
(
    REVIEW_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    CONTENT varchar,
    isPOSITIVE boolean,
    USER_ID INTEGER REFERENCES USERS (USER_ID),
    FILM_ID INTEGER REFERENCES FILMS (FILM_ID),
    USEFUL INTEGER
);

CREATE TABLE IF NOT EXISTS REVIEWS_LIKE
(
    REVIEW_ID INTEGER REFERENCES REVIEWS (REVIEW_ID) ON DELETE CASCADE,
    USER_ID INTEGER REFERENCES USERS (USER_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS REVIEWS_DISLIKE
(
    REVIEW_ID INTEGER REFERENCES REVIEWS (REVIEW_ID) ON DELETE CASCADE,
    USER_ID INTEGER REFERENCES USERS (USER_ID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS directors (
                                      director_id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                      director_name varchar CHECK director_name <> ' '
);

CREATE TABLE IF NOT EXISTS films_directors (
                                            film_id INTEGER REFERENCES films(FILM_ID) ON DELETE CASCADE,
                                            director_id INTEGER REFERENCES directors(director_id) ON DELETE CASCADE ,
                                            PRIMARY KEY (film_id, director_id)
);

CREATE TABLE IF NOT EXISTS feed
(
    event_id       INTEGER auto_increment,
    entity_id      INTEGER,
    operation_name CHARACTER VARYING(10),
    event_type     CHARACTER VARYING(10),
    user_id        INTEGER NOT NULL,
    timestamp      LONG,
    CONSTRAINT feed_users_user_id_fk
        FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS feed_event_id_uindex
    ON feed (event_id);

CREATE UNIQUE INDEX IF NOT EXISTS feed_timestamp_uindex
    on feed (TIMESTAMP);

ALTER TABLE feed
    ADD CONSTRAINT IF NOT EXISTS feed_pk
        PRIMARY KEY (event_id);
