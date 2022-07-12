CREATE TABLE IF NOT EXISTS USERS (user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
login varchar NOT NULL,
name varchar,
email varchar NOT NULL UNIQUE,
birthday date NOT NULL);

CREATE TABLE IF NOT EXISTS mpa_rates (
    mpa_rate_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS films (film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
name varchar NOT NULL UNIQUE,
description varchar(200) NOT NULL,
release_date date NOT NULL,
duration INTEGER,
mpa_rate_id INTEGER REFERENCES mpa_rates (mpa_rate_id));

CREATE TABLE IF NOT EXISTS films_likes (
    film_id INTEGER REFERENCES films (film_id),
    user_id INTEGER REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS genres (
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name varchar NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS films_genres (
    film_id INTEGER REFERENCES films (film_id),
    genre_id INTEGER REFERENCES genres (genre_id)
);

CREATE TABLE IF NOT EXISTS friendships (
    user_id INTEGER REFERENCES users (user_id),
    friend_id INTEGER REFERENCES users (user_id)
);



