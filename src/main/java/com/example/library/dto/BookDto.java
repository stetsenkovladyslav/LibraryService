package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class BookDto {

    @NotBlank(message = "Name field must not be empty")
    @Size(max = 80, message = "Name should not be more than 80 characters")
    @NotNull(message = "The value should not be null")
    private String name;

    @NotBlank(message = "Description field must not be empty")
    @Size(max = 400, message = "Description should not be more than 400 characters")
    @NotNull(message = "The value should not be null")
    private String description;

    @NotNull(message = "The value should not be null")
    @NotEmpty(message = "Book must have at list 1 author")
    private List<Long> authorsId;

    @NotNull(message = "The value should not be null")
    @NotEmpty(message = "Genre must have at list 1 genre")
    private List<Long> genresId;
}