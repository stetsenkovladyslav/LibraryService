package com.example.library.service.user;

import com.example.library.controller.exception.UserAlreadyExistException;
import com.example.library.dto.user.UserDto;
import com.example.library.model.user.Role;
import com.example.library.model.user.User;
import com.example.library.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public User login(String username, String password) {
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new AccessDeniedException("User does not exist"));
        if (!encoder.matches(password, user.getPassword()))
            throw new AccessDeniedException("Incorrect password");
        return user;
    }

    @Override
    public User register(UserDto userDto) throws UserAlreadyExistException {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User with this username already exist");
        }
        User newUser = new User();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(encoder.encode(userDto.getPassword()));
        newUser.setRole(userDto.getRole());
        newUser.setLocked(false);
        newUser.setEnabled(userDto.getRole() == Role.ROLE_USER);
        return userRepository.save(newUser);
    }

    @Override
    public void approveUserById(long id) {
        User user = userRepository.getById(id);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public Page<User> getAllNotEnabled(Pageable pageable) {
        return userRepository.findAllByEnabledIsFalse(pageable);
    }

    @Override
    public User getNotEnabledById(long id) {
        User user = userRepository.getById(id);
        if (user.isEnabled()) {
            return null;
        }
        return user;
    }

    @Override
    public User createAdmin() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("admin");
        user.setPassword(encoder.encode("admin"));
        user.setEnabled(true);
        user.setLocked(false);
        user.setRole(Role.ROLE_ADMIN);
        return userRepository.save(user);
    }

}