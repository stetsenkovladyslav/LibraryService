package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {
    private String firstName;
    private String lastName;
}