package com.catalog.common.enums;

/**
 * Represents the state of a bulk import job.
 *
 * Used when importing products from CSV/JSON using Spring Batch.
 */
public enum ImportJobStatus {

    /**
     * Job was created but has not started yet.
     */
    PENDING,

    /**
     * Job is currently processing.
     */
    RUNNING,

    /**
     * Job finished successfully with no errors.
     */
    COMPLETED,

    /**
     * Job finished, but some rows failed validation/import.
     */
    COMPLETED_WITH_ERRORS,

    /**
     * Job failed completely due to a serious error.
     */
    FAILED,

    /**
     * Job was cancelled before completion.
     */
    CANCELLED
}