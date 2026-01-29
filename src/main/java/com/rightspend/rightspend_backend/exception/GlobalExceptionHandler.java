package com.rightspend.rightspend_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔐 AUTH ERRORS
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<Map<String, Object>> handleLoginFailed(LoginFailedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of(
                        "error", "LOGIN_FAILED",
                        "message", ex.getMessage(),
                        "status", 401,
                        "timestamp", LocalDateTime.now()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "error", "USER_ALREADY_EXISTS",
                        "message", ex.getMessage(),
                        "status", 409,
                        "timestamp", LocalDateTime.now()));
    }

    // 📦 BUSINESS ERRORS
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "error", "RESOURCE_NOT_FOUND",
                        "message", ex.getMessage(),
                        "status", 404,
                        "timestamp", LocalDateTime.now()));
    }

    // 💥 FALLBACK (DO NOT LEAK DETAILS)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        ex.printStackTrace(); // ✔ keep for dev
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Map.of(
                        "error", "INTERNAL_SERVER_ERROR",
                        "message", "Something went wrong",
                        "status", 500,
                        "timestamp", LocalDateTime.now()));
    }
}
