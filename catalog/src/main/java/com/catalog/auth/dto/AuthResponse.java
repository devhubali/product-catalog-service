package com.catalog.auth.dto;

/**
 * Response body returned after a successful register or login.
 *
 * The client should store the JWT token and send it in the
 * Authorization header for all protected requests:
 * Authorization: Bearer <token>
 */
public record AuthResponse(
        String token,
        UserResponse user
) {
}
