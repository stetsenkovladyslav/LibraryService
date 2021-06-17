package com.example.library.dto;

import com.example.library.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}
