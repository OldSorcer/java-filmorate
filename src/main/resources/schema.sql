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