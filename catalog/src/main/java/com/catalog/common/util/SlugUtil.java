package com.catalog.common.util;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Utility class for generating URL-friendly slugs.
 *
 * Example:
 * "Apple iPhone 15 Pro Max" becomes "apple-iphone-15-pro-max"
 *
 * Slugs are useful for public URLs:
 * /api/v1/products/apple-iphone-15-pro-max
 */
public final class SlugUtil {

    /**
     * Private constructor prevents creating objects from this utility class.
     *
     * Usage should be:
     * SlugUtil.toSlug("Some Text")
     */
    private SlugUtil() {
    }

    /**
     * Converts normal text into a slug.
     *
     * Steps:
     * 1. Normalize special characters.
     * 2. Remove accents/diacritics.
     * 3. Convert to lowercase.
     * 4. Remove invalid characters.
     * 5. Replace spaces with hyphens.
     * 6. Remove duplicate hyphens.
     */
    public static String toSlug(String input) {
        if (input == null || input.isBlank()) {
            return "";
        }

        // Normalize characters such as é, ü, ñ into base letters + marks
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        return normalized
                // Remove accent marks
                .replaceAll("\\p{M}", "")

                // Convert to lowercase using a stable locale
                .toLowerCase(Locale.ROOT)

                // Remove leading/trailing spaces
                .trim()

                // Remove anything that is not a letter, number, space, or hyphen
                .replaceAll("[^a-z0-9\\s-]", "")

                // Replace one or more spaces with a single hyphen
                .replaceAll("\\s+", "-")

                // Replace multiple hyphens with one hyphen
                .replaceAll("-+", "-")

                // Remove hyphen from beginning or end
                .replaceAll("^-|-$", "");
    }

    /**
     * Creates a slug and appends a suffix to make it unique.
     *
     * Example:
     * input = "iPhone 15"
     * suffix = "abc123"
     * result = "iphone-15-abc123"
     */
    public static String toUniqueSlug(String input, String uniqueSuffix) {
        String slug = toSlug(input);

        if (uniqueSuffix == null || uniqueSuffix.isBlank()) {
            return slug;
        }

        return slug + "-" + uniqueSuffix;
    }
}