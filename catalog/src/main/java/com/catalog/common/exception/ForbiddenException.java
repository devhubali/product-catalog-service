package com.catalog.common.exception;

/**
 * Thrown when a user is authenticated but does not have permission.
 *
 * HTTP equivalent: 403 Forbidden.
 */
public class ForbiddenException extends RuntimeException {

    /**
     * Allows you to pass a custom error message.
     */
    public ForbiddenException(String message) {
        super(message);
    }

    /**
     * Default forbidden message.
     *
     * Use when the user is logged in but lacks the required role.
     */
    public static ForbiddenException defaultMessage() {
        return new ForbiddenException("You do not have permission to perform this action");
    }
}
