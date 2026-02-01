package com.rightspend.rightspend_backend.controller;

import com.rightspend.rightspend_backend.dto.LoginRequest;
import com.rightspend.rightspend_backend.dto.SignupRequest;
import com.rightspend.rightspend_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map; 

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.signup(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {

        String token = userService.login(request);

        return ResponseEntity.ok(
                Map.of("accessToken", token));
    }
}
