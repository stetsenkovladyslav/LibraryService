package com.example.library.controller.user;


import com.example.library.dto.authentication.AuthenticationRequest;
import com.example.library.dto.user.UserDto;
import com.example.library.mapper.user.UserMapper;
import com.example.library.model.user.User;
import com.example.library.service.user.UserService;
import com.example.library.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createAuthenticationToken(@RequestBody @Valid AuthenticationRequest auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        final UserDetails userDetails = userService.login(auth.getUsername(), auth.getPassword());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDto userDto) {
        User newUser = userService.register(userDto);
        if (!newUser.isEnabled()) {
            return ResponseEntity.ok("Waiting for approval");
        }
        return ResponseEntity.ok(jwtUtil.generateToken(newUser));
    }

    @GetMapping(
            value = "/approve/{id}"
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> approveUser(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id
    ) {
        userService.approveUserById(id);
        return ResponseEntity.ok("User approved");
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"limit", "page"}
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<UserDto>> getAllRegistrationRequests(@RequestParam Pageable pageable) {
        Page<User> allNotEnabled = userService.getAllNotEnabled(pageable);
        if (allNotEnabled.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allNotEnabled.map(userMapper::toDto));
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDto> getRegistrationRequest(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id
    ) {
        User user = userService.getNotEnabledById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping(value = "/new-admin")
    public ResponseEntity<String> createAdmin() {
        User admin = userService.createAdmin();
        return ResponseEntity.ok(jwtUtil.generateToken(admin));
    }
}