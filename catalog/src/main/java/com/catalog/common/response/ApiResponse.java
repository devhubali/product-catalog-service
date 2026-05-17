package com.catalogiq.common.response;

import java.time.LocalDateTime;

/**
 * Generic wrapper for normal API responses.
 *
 * Instead of returning raw objects directly from controllers,
 * we wrap them in this structure so all successful and failed responses
 * have a consistent format.
 *
 * Example:
 * {
 *   "success": true,
 *   "message": "Request completed successfully",
 *   "data": {...},
 *   "timestamp": "2026-05-17T18:30:00"
 * }
 */
public record ApiResponse<T>(
        boolean success,          // Indicates whether the request succeeded
        String message,           // Human-readable response message
        T data,                   // Actual response body/data
        LocalDateTime timestamp   // Time when response was created
) {

    /**
     * Creates a successful response with a custom message.
     *
     * Use this when you want to return data and a specific message.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }

    /**
     * Creates a successful response with a default message.
     *
     * Use this when the message is not important.
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "Request completed successfully",
                data,
                LocalDateTime.now()
        );
    }

    /**
     * Creates a failed response.
     *
     * The data is null because errors usually do not return business data.
     */
    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
    }
}