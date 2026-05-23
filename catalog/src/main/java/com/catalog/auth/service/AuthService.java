package com.catalog.auth.service;

import com.catalog.auth.dto.AuthResponse;
import com.catalog.auth.dto.ChangePasswordRequest;
import com.catalog.auth.dto.LoginRequest;
import com.catalog.auth.dto.RegisterRequest;
import com.catalog.auth.dto.UserResponse;

/**
 * Contract for authentication-related operations.
 *
 * Using an interface here makes it easy to swap the implementation
 * or write unit tests with mocks.
 */
public interface AuthService {

    /**
     * Registers a new user and returns a JWT token.
     */
    AuthResponse register(RegisterRequest request);

    /**
     * Authenticates a user and returns a JWT token.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Returns the currently authenticated user's profile.
     *
     * @param email The email extracted from the JWT token.
     */
    UserResponse getCurrentUser(String email);

    /**
     * Changes the password for the currently authenticated user.
     *
     * @param email          The email extracted from the JWT token.
     * @param request        The current and new password.
     */
    void changePassword(String email, ChangePasswordRequest request);
}
