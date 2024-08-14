package com.igor.user.service.controller;

import com.igor.user.service.model.User;
import com.igor.user.service.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    @SecurityRequirement(name = "bearerAuth")
    public User getUserProfile() {
        return userService.getUserProfile();
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
}
