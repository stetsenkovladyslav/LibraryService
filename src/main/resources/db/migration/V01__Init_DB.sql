create table books (
    id serial primary key,
    name varchar (80) not null,
    author varchar (80) not null,
    genre varchar (80) not null,
    description varchar (400)
    );

create table users (
    id serial primary key,
    first_name varchar (80) not null,
    last_name varchar (80) not null
    );

create table authors (
    id serial primary key,
    first_name varchar (80) not null,
    last_name varchar (80) not null
    );

create table genres (
    id serial primary key,
    name varchar (80) not null
    );

create table books_authors (
    book_id int primary key,
    author_id int primary key,
    constraint (books_authors_book_id_fk) FOREIGN KEY (book_id) REFERENCES books(id),
    constraint (books_authors_author_id_fk) FOREIGN KEY (author_id) REFERENCES authors(id)
    );

create table books_genres (
    book_id int primary key,
    genre_id int primary key,
    constraint (books_geners_book_id_fk) FOREIGN KEY (book_id) REFERENCES books(id),
    constraint (books_geners_genre_id_fk) FOREIGN KEY (genre_id) REFERENCES geners(id)
    );