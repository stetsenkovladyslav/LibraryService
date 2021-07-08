package com.example.library.dto.genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    @NotBlank(message = "Name field must not be empty")
    @Size(max = 80, message = "Name should not be more than 80 characters")
    @NotNull(message = "The value should not be null")
    private String genreName;
}