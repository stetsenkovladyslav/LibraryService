package com.example.library.model.book;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class BookSearchCriteria {
    private String genre;
    private String authorFirstName;
    private String authorLastName;


}
