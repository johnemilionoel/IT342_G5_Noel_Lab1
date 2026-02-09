package com.noel.g2app.controller;

import com.noel.g2app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    // GET CURRENT USER PROFILE
    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@RequestParam Long userId) {

        return authService.getUserProfile(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
