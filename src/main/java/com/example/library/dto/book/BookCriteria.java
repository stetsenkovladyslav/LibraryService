package com.example.library.dto.book;


import com.example.library.model.author.Author;
import com.example.library.model.book.Book;
import com.example.library.model.genre.Genre;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class BookCriteria {
    private String genre;
    private String authorFirstName;
    private String authorLastName;

    public Specification<Book> buildCriteria() {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(genre)) {
                ListJoin<Book, Genre> joinedGenreList = root.joinList("genres");
                predicates.add(criteriaBuilder.equal(joinedGenreList.get("genreName"), genre)
                );
            }
            if (Objects.nonNull(authorFirstName) &&
                Objects.nonNull(authorLastName)) {
                ListJoin<Book, Author> joinedAuthorList = root.joinList("authors");
                predicates.addAll(
                        List.of(
                                criteriaBuilder.equal(joinedAuthorList.get("firstName"), authorFirstName),
                                criteriaBuilder.equal(joinedAuthorList.get("lastName"), authorLastName)
                        )
                );
            }
            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}