package com.catalog.common.enums;

/**
 * Represents the lifecycle/status of a product.
 *
 * Use this instead of raw strings so the code is safer and easier to maintain.
 */
public enum ProductStatus {

    /**
     * Product is being prepared and is not visible publicly.
     */
    DRAFT,

    /**
     * Product is visible and available in public catalog APIs.
     */
    ACTIVE,

    /**
     * Product is temporarily hidden or disabled.
     */
    INACTIVE,

    /**
     * Product is no longer used, but kept for historical/reference purposes.
     */
    ARCHIVED,

    /**
     * Product is soft-deleted.
     *
     * We keep the record in the database instead of physically deleting it.
     */
    DELETED
}
