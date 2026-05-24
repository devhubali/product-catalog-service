package com.catalog.auth.controller;

import com.catalog.auth.dto.AuthResponse;
import com.catalog.auth.dto.ChangePasswordRequest;
import com.catalog.auth.dto.LoginRequest;
import com.catalog.auth.dto.RegisterRequest;
import com.catalog.auth.dto.UserResponse;
import com.catalog.auth.service.AuthService;
import com.catalog.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles authentication-related HTTP requests.
 *
 * Public routes (no token needed):
 * - POST /api/v1/auth/register
 * - POST /api/v1/auth/login
 *
 * Protected routes (token required):
 * - GET  /api/v1/auth/me
 * - POST /api/v1/auth/change-password
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user account.
     *
     * Returns HTTP 201 Created with a JWT token and user profile.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful", response));
    }

    /**
     * Authenticates a user with email and password.
     *
     * Returns HTTP 200 with a JWT token and user profile.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    /**
     * Returns the currently authenticated user's profile.
     *
     * The user is identified from the JWT token via Spring Security's Authentication object.
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        UserResponse user = authService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * Changes the authenticated user's password.
     *
     * The user must provide their current password to verify their identity.
     */
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        authService.changePassword(authentication.getName(), request);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }
}
