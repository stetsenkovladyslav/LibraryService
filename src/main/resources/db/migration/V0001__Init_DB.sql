CREATE TABLE IF NOT EXISTS books
(
    id          serial PRIMARY KEY,
    name        VARCHAR (80) NOT NULL,
    description VARCHAR (400) NOT NULL
);

CREATE TABLE IF NOT EXISTS authors
(
    id         serial PRIMARY KEY,
    first_name VARCHAR (80) NOT NULL,
    last_name  VARCHAR (80) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    id   serial PRIMARY KEY,
    name VARCHAR (80) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id         serial primary key,
    first_name VARCHAR (80) NOT NULL,
    last_name  VARCHAR (80) NOT NULL,
    username   VARCHAR (24) UNIQUE NOT NULL,
    password   VARCHAR (64) NOT NULL,
    role       INTEGER      NOT NULL,
    locked     boolean      NOT NULL,
    enabled    boolean      NOT NULL
);

CREATE TABLE IF NOT EXISTS books_authors
(
    book_id   INT,
    author_id INT,
    CONSTRAINT books_authors_book_id_fk FOREIGN KEY (book_id) REFERENCES books (id),
    CONSTRAINT books_authors_author_id_fk FOREIGN KEY (author_id) REFERENCES authors (id)
);

CREATE TABLE IF NOT EXISTS books_genres
(
    book_id  INT,
    genre_id INT,
    CONSTRAINT books_genres_book_id_fk FOREIGN KEY (book_id) REFERENCES books (id),
    CONSTRAINT books_genres_genre_id_fk FOREIGN KEY (genre_id) REFERENCES genres (id)
);