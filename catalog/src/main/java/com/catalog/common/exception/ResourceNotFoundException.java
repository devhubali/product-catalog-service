package com.catalog.common.exception;

/**
 * Thrown when a requested resource does not exist.
 *
 * Example:
 * Product with id xxx does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Allows you to pass a custom error message.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Helper factory method to keep exception messages consistent.
     *
     * Example:
     * throw ResourceNotFoundException.of("Product", productId);
     */
    public static ResourceNotFoundException of(String resourceName, Object id) {
        return new ResourceNotFoundException(resourceName + " not found with id: " + id);
    }
}