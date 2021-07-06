package com.example.library.model.book;


import com.example.library.model.genre.Genre;
import com.example.library.model.author.Author;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany()
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))

    private List<Author> authors;

    @ManyToMany()
    @JoinTable(
            name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))

    private List<Genre> genres;


    @Column(name = "name")
    private String bookName;

    @Column(name = "description")
    private String bookDescription;

    public Book(String bookName, String bookDescription) {
        this.bookName = bookName;
        this.bookDescription = bookDescription;
    }
}