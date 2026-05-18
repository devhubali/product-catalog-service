package com.catalog.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Utility class for creating Pageable objects from request parameters.
 *
 * This avoids repeating pagination logic in every controller.
 */
public final class PaginationUtil {

    /**
     * Default page number if the client does not provide one.
     *
     * Spring pages start from 0.
     */
    private static final int DEFAULT_PAGE_NUMBER = 0;

    /**
     * Default number of records per page.
     */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * Maximum allowed page size.
     *
     * This prevents clients from requesting too much data in one request.
     */
    private static final int MAX_PAGE_SIZE = 100;

    /**
     * Private constructor prevents creating objects from this utility class.
     */
    private PaginationUtil() {
    }

    /**
     * Creates a Pageable object safely from request parameters.
     *
     * Handles:
     * - null page
     * - negative page
     * - null size
     * - invalid size
     * - maximum size limit
     * - sorting
     */
    public static Pageable createPageable(
            Integer page,
            Integer size,
            String sortBy,
            String sortDirection
    ) {
        // If page is missing or invalid, use default page 0
        int pageNumber = page == null || page < 0
                ? DEFAULT_PAGE_NUMBER
                : page;

        // If size is missing or invalid, use default size.
        // If size is too large, cap it at MAX_PAGE_SIZE.
        int pageSize = size == null || size <= 0
                ? DEFAULT_PAGE_SIZE
                : Math.min(size, MAX_PAGE_SIZE);

        // Create Sort object from sortBy and sortDirection
        Sort sort = createSort(sortBy, sortDirection);

        return PageRequest.of(pageNumber, pageSize, sort);
    }

    /**
     * Creates sorting configuration.
     *
     * If no sort field is provided, default to createdAt descending.
     */
    private static Sort createSort(String sortBy, String sortDirection) {
        if (sortBy == null || sortBy.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        // Only "asc" should create ascending sort.
        // Anything else defaults to descending.
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return Sort.by(direction, sortBy);
    }
}