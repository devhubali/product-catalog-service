package com.catalog.common.exception;

/**
 * Thrown when trying to create a record that already exists.
 *
 * Example:
 * Creating a product with an SKU that is already used.
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Allows you to pass a custom error message.
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

    /**
     * Helper factory method to keep duplicate error messages consistent.
     *
     * Example:
     * throw DuplicateResourceException.of("Product", "sku", request.sku());
     */
    public static DuplicateResourceException of(String resourceName, String fieldName, Object value) {
        return new DuplicateResourceException(
                resourceName + " already exists with " + fieldName + ": " + value
        );
    }
}
