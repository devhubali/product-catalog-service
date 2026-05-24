package com.catalog.common.exception;

/**
 * Thrown when the client sends a request with invalid data or logic.
 *
 * HTTP equivalent: 400 Bad Request.
 *
 * Example:
 * Trying to change password but the current password is wrong.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Allows you to pass a custom error message.
     */
    public BadRequestException(String message) {
        super(message);
    }
}
