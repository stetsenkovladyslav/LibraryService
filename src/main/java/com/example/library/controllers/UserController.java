package com.example.library.controllers;


import com.example.library.dto.AuthenticationRequest;
import com.example.library.dto.UserDto;
import com.example.library.entities.User;
import com.example.library.services.UserService;
import com.example.library.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthenticationRequest auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        final UserDetails userDetails = userService.login(auth.getUsername(), auth.getPassword());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
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
    public ResponseEntity<String> approveUser(@PathVariable Long id) {
        userService.approveUserById(id);
        return ResponseEntity.ok("User approved");
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"limit", "page"}
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<User>> getAllRegistrationRequests(@RequestParam int page, @RequestParam int limit) {
        Page<User> allNotEnabled = userService.getAllNotEnabled(page, limit);
        if (allNotEnabled.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allNotEnabled);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> getRegistrationRequest(@PathVariable Long id) {
        User user = userService.getNotEnabledById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}