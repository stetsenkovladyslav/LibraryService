package com.example.library.entity;


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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    public Book(Long id, String bookName, String bookGenre, String bookDescription) {
        this.id = id;
        this.bookName = bookName;
        this.bookGenre = bookGenre;
        this.bookDescription = bookDescription;
    }

    @ManyToMany()
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))

    private List<Author> authors;

    @ManyToMany()
    @JoinTable(
            name = "books_geners",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))

    private List<Genre> genres;


    @Column(name = "name")
    private String bookName;

    @Column(name = "author")
    private String authorName;

    @Column(name = "genre")
    private String bookGenre;

    @Column(name = "description")
    private String bookDescription;


}
