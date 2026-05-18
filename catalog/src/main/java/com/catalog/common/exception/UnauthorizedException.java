package com.catalog.common.exception;

/**
 * Thrown when a user is not authenticated.
 *
 * HTTP equivalent: 401 Unauthorized.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Allows you to pass a custom error message.
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Default unauthorized message.
     *
     * Use when the user has no valid authentication token.
     */
    public static UnauthorizedException defaultMessage() {
        return new UnauthorizedException("Authentication is required");
    }
}