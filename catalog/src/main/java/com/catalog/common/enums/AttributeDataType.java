package com.catalog.common.enums;

/**
 * Represents the supported data types for dynamic product attributes.
 *
 * Example:
 * Laptop category may define:
 * - cpu: STRING
 * - ram: ENUM
 * - screenSize: NUMBER
 */
public enum AttributeDataType {

    /**
     * Text value.
     *
     * Example:
     * "Intel i7"
     */
    STRING,

    /**
     * Numeric value.
     *
     * Example:
     * 15.6
     */
    NUMBER,

    /**
     * True/false value.
     *
     * Example:
     * true
     */
    BOOLEAN,

    /**
     * Date value.
     *
     * Example:
     * "2026-05-17"
     */
    DATE,

    /**
     * One value selected from a fixed allowed list.
     *
     * Example:
     * "Black" from ["Black", "White", "Blue"]
     */
    ENUM,

    /**
     * Multiple values selected from a fixed allowed list.
     *
     * Example:
     * ["Bluetooth", "WiFi", "NFC"]
     */
    MULTI_SELECT
}