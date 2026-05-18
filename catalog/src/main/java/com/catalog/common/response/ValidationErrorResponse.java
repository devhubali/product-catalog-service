package com.catalog.common.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Special response structure for validation errors.
 *
 * Validation errors are different from normal errors because they usually
 * contain multiple field-level problems.
 *
 * Example:
 * name is required
 * price must be positive
 * sku must not be blank
 */
public record ValidationErrorResponse(
        boolean success,                    // Always false for validation errors
        String message,                     // General validation message
        String path,                        // API path where the error happened
        List<FieldErrorDetail> errors,      // List of field-specific errors
        LocalDateTime timestamp             // Time when error response was created
) {

    /**
     * Factory method for creating validation error responses.
     */
    public static ValidationErrorResponse of(
            String message,
            String path,
            List<FieldErrorDetail> errors
    ) {
        return new ValidationErrorResponse(
                false,
                message,
                path,
                errors,
                LocalDateTime.now()
        );
    }

    /**
     * Represents one invalid field in the request body.
     *
     * Example:
     * field: "name"
     * message: "Product name is required"
     * rejectedValue: null
     */
    public record FieldErrorDetail(
            String field,
            String message,
            Object rejectedValue
    ) {
    }
}