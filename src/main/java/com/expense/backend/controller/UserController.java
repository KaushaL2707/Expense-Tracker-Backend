package com.expense.backend.controller;

import com.expense.backend.model.User;
import com.expense.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setName(updatedUser.getName());
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        // Password update logic should be handled securely, skipping for now as per requirements
        
        return userService.createUser(user);
    }
}
