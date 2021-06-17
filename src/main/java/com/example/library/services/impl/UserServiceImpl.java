package com.example.library.services.impl;

import com.example.library.dao.UserDao;
import com.example.library.dto.UserDto;
import com.example.library.entities.Role;
import com.example.library.entities.User;
import com.example.library.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserDao userDao, @Lazy BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username).orElseThrow();
    }

    @Override
    public User login(String username, String password) {
        var user = userDao.findByUsername(username).orElseThrow(() -> new AccessDeniedException("User does not exist"));
        if (!encoder.matches(password, user.getPassword()))
            throw new AccessDeniedException("Incorrect password");
        return user;
    }

    @Override
    public User register(UserDto userDto) {
        User newUser = new User();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(encoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setLocked(false);
        newUser.setEnabled(userDto.getRole() == Role.ROLE_USER);
        return userDao.save(newUser);
    }

    @Override
    public void approveUserById(long id) {
        User user = userDao.getById(id);
        user.setEnabled(true);
        userDao.save(user);
    }

    @Override
    public Page<User> getAllNotEnabled(int page, int limit) {
        return userDao.findAllByEnabledIsFalse(PageRequest.of(page, limit));
    }

    @Override
    public User getNotEnabledById(long id) {
        User user = userDao.getById(id);
        if (user.isEnabled()) {
            return null;
        }
        return user;
    }
}
