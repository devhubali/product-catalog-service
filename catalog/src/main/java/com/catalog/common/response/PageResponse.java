package com.catalog.common.response;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Generic wrapper for paginated API responses.
 *
 * Spring Data returns Page<T>, but exposing it directly can be too verbose.
 * This class gives the frontend only the useful pagination details.
 */
public record PageResponse<T>(
        List<T> content,       // The actual list of items for the current page
        int pageNumber,        // Current page number, starts from 0
        int pageSize,          // Number of items requested per page
        long totalElements,    // Total number of records in the database
        int totalPages,        // Total number of available pages
        boolean first,         // True if this is the first page
        boolean last,          // True if this is the last page
        boolean empty          // True if the page has no content
) {

    /**
     * Converts Spring Data Page<T> into our custom PageResponse<T>.
     *
     * This keeps controller responses clean and consistent.
     */
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty()
        );
    }
}