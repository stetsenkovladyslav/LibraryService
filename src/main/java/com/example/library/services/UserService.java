package com.example.library.services;

import com.example.library.dto.UserDto;
import com.example.library.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User login(String username, String password);

    User register(UserDto userDto);

    void approveUserById(long id);

    Page<User> getAllNotEnabled(int page, int limit);

    User getNotEnabledById(long id);
}
