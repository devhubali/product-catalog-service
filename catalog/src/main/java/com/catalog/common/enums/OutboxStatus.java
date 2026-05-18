package com.catalog.common.enums;

/**
 * Represents the publishing status of an outbox event.
 *
 * The outbox pattern stores events in the database first,
 * then publishes them to Kafka later.
 */
public enum OutboxStatus {

    /**
     * Event is saved in DB but not yet published to Kafka.
     */
    PENDING,

    /**
     * Event was successfully published to Kafka.
     */
    PUBLISHED,

    /**
     * Publishing failed.
     *
     * The system may retry it later.
     */
    FAILED
}