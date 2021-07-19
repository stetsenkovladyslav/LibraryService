package com.example.library.service.user;

import com.example.library.dto.user.UserDto;
import com.example.library.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    User login(String username, String password);

    User register(UserDto userDto);

    void approveUserById(long id);

    Page<User> getAllNotEnabled(Pageable pageable);

    User getNotEnabledById(long id);

    User createAdmin();

}