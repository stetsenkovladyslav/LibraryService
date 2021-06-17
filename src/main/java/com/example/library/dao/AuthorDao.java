package com.example.library.dao;

import com.example.library.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {

    List<Author> getAuthorByIdIn(List<Long> ids);

    @Query("UPDATE Author SET firstName = :firstName, lastName = :lastName WHERE id = :id")
    void updateAuthorById(long id, String firstName, String lastName);
}