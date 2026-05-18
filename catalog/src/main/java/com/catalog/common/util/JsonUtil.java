package com.catalog.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Utility component for common JSON operations.
 *
 * This project uses JSONB in PostgreSQL for:
 * - product attributes
 * - variant attributes
 * - audit old_data/new_data
 * - outbox event payload
 * - import row data
 *
 * JsonUtil centralizes common Jackson operations.
 */
@Component
public class JsonUtil {

    /**
     * Jackson ObjectMapper is the main object used to convert
     * Java objects to JSON and JSON to Java objects.
     *
     * Spring Boot auto-configures ObjectMapper for us.
     */
    private final ObjectMapper objectMapper;

    /**
     * Constructor injection.
     *
     * Spring will automatically provide the ObjectMapper bean.
     */
    public JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Converts any Java object into JsonNode.
     *
     * Example:
     * ProductResponse -> JsonNode
     */
    public JsonNode toJsonNode(Object value) {
        if (value == null) {
            return objectMapper.nullNode();
        }

        return objectMapper.valueToTree(value);
    }

    /**
     * Converts JsonNode into a specific Java class.
     *
     * Example:
     * JsonNode attributes -> LaptopAttributes.class
     */
    public <T> T fromJsonNode(JsonNode jsonNode, Class<T> targetClass) {
        if (jsonNode == null || jsonNode.isNull()) {
            return null;
        }

        return objectMapper.convertValue(jsonNode, targetClass);
    }

    /**
     * Converts a Java object into a JSON string.
     *
     * Example:
     * ProductResponse -> "{\"name\":\"iPhone\"}"
     */
    public String toJsonString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Failed to convert object to JSON string", ex);
        }
    }

    /**
     * Parses a raw JSON string into JsonNode.
     *
     * Example:
     * "{\"cpu\":\"Intel i7\"}" -> JsonNode
     */
    public JsonNode parseJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Invalid JSON format", ex);
        }
    }

    /**
     * Checks whether a string is valid JSON.
     *
     * Useful during CSV/JSON import validation.
     */
    public boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (JsonProcessingException ex) {
            return false;
        }
    }
}