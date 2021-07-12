package com.example.library.repository.book;

import com.example.library.model.author.Author;
import com.example.library.model.book.Book;
import com.example.library.model.book.BookPage;
import com.example.library.model.book.BookSearchCriteria;
import com.example.library.model.genre.Genre;
import org.apache.maven.model.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public BookCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Book> findAllWithFilters(BookPage bookPage,
                                         BookSearchCriteria BookSearchCriteria) {
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> BookRoot = criteriaQuery.from(Book.class);
        Predicate predicate = getPredicate(BookSearchCriteria, BookRoot);
        criteriaQuery.where(predicate);
        setOrder(bookPage, criteriaQuery, BookRoot);

        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(bookPage.getPageNumber() * bookPage.getPageSize());
        typedQuery.setMaxResults(bookPage.getPageSize());

        Pageable pageable = getPageable(bookPage);

        long BooksCount = getBooksCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, BooksCount);
    }

    private Predicate getPredicate(BookSearchCriteria bookSearchCriteria,
                                   Root<Book> bookRoot) {
        List<Predicate> predicates = new ArrayList<>();


        if (Objects.nonNull(bookSearchCriteria.getGenre())) {
            ListJoin<Book, Genre> joinedGenreList = bookRoot.joinList("genres");
            predicates.add(
                    criteriaBuilder.equal(joinedGenreList.get("genreName"), bookSearchCriteria.getGenre())
            );
        }
        if (Objects.nonNull(bookSearchCriteria.getAuthorFirstName()) &&
            Objects.nonNull(bookSearchCriteria.getAuthorLastName())) {
            ListJoin<Book, Author> joinedAuthorList = bookRoot.joinList("authors");
            predicates.addAll(
                    List.of(
                            criteriaBuilder.equal(joinedAuthorList.get("firstName"), bookSearchCriteria.getAuthorFirstName()),
                            criteriaBuilder.equal(joinedAuthorList.get("lastName"), bookSearchCriteria.getAuthorLastName())
                    )
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Pageable getPageable(BookPage BookPage) {
        Sort sort = Sort.by(BookPage.getSortDirection(), BookPage.getSortBy());
        return PageRequest.of(BookPage.getPageNumber(),BookPage.getPageSize(), sort);
    }

    private long getBooksCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Book> countRoot = countQuery.from(Book.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private void setOrder(BookPage BookPage,
                          CriteriaQuery<Book> criteriaQuery,
                          Root<Book> BookRoot) {
        if (BookPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(BookRoot.get(BookPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(BookRoot.get(BookPage.getSortBy())));
        }
    }
}