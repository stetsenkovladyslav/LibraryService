package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {
    private String name;
    private String description;
    private List<Long> authorsId;
    private List<Long> genresId;
}
