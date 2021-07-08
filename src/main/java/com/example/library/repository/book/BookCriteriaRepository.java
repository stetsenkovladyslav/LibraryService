package com.example.library.repository.book;

import com.example.library.model.book.Book;
import com.example.library.model.book.BookPage;
import com.example.library.model.book.BookSearchCriteria;
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

    public Page<Book> findAllWithFilters(BookPage BookPage,
                                         BookSearchCriteria BookSearchCriteria){
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> BookRoot = criteriaQuery.from(Book.class);
        Predicate predicate = getPredicate(BookSearchCriteria, BookRoot);
        criteriaQuery.where(predicate);
        setOrder(BookPage, criteriaQuery, BookRoot);

        TypedQuery<Book> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(BookPage.getPageNumber() * BookPage.getPageSize());
        typedQuery.setMaxResults(BookPage.getPageSize());

        Pageable pageable = getPageable(BookPage);

        long BooksCount = getBooksCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, BooksCount);
    }

    /*
    как сделать чтобы в аргументы передавать не автора, а ферст и ласт нейм как два аргумента
    как списки авторов и жанров сравнить с  ферстнейи и ластнейм
    * */
    private Predicate getPredicate(BookSearchCriteria BookSearchCriteria,
                                   Root<Book> BookRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(BookSearchCriteria.getGenre())){
            predicates.add(criteriaBuilder.like(BookRoot.get("genres"),
                            "%" + BookSearchCriteria.getGenre() + "%")
            );
        }
    /*    if(Objects.nonNull(BookSearchCriteria.getAuthorFirstName()) &&
           Objects.nonNull(BookSearchCriteria.getAuthorLastName())){
            predicates.add(
                    criteriaBuilder.like(BookRoot.get("authors"),
                            "%" + BookSearchCriteria.getAuthor() + "%")
            );*/
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
