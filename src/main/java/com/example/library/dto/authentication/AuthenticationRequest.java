package com.example.library.dto.authentication;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AuthenticationRequest {

    @NotBlank(message = "Username field must not be empty")
    @Size(max = 24, message = "Username should not be more than 24 characters")
    @NotNull(message = "The value should not be null")
    private String username;

    @NotBlank(message = "Password field must not be empty")
    @Size(max = 64, message = "Password should not be more than 24 characters")
    @NotNull(message = "The value should not be null")
    private String password;
}