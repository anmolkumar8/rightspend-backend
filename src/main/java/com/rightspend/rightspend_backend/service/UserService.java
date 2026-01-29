package com.rightspend.rightspend_backend.service;

import com.rightspend.rightspend_backend.dto.LoginRequest;
import com.rightspend.rightspend_backend.dto.SignupRequest;
import com.rightspend.rightspend_backend.exception.LoginFailedException;
import com.rightspend.rightspend_backend.exception.UserAlreadyExistsException;
import com.rightspend.rightspend_backend.model.User;
import com.rightspend.rightspend_backend.repository.UserRepository;
import com.rightspend.rightspend_backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    } 

    // ================= SIGNUP =================
    public void signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setStatus("ACTIVE");

        userRepository.save(user);
    }

    // ================= LOGIN =================
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new LoginFailedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new LoginFailedException("Invalid email or password");
        }

        // ✅ Generate JWT
        return jwtService.generateToken(user.getEmail(), user.getRole());
    }
}
