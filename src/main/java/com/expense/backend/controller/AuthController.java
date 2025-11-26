package com.expense.backend.controller;

import com.expense.backend.dto.AuthRequest;
import com.expense.backend.dto.AuthResponse;
import com.expense.backend.model.User;
import com.expense.backend.service.UserService;
import com.expense.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        Optional<User> userOpt = userService.getUserByEmail(authRequest.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getEmail());
                return new AuthResponse(token, user.getId(), user.getName(), user.getEmail());
            }
        }
        throw new RuntimeException("Invalid credentials");
    }
}
