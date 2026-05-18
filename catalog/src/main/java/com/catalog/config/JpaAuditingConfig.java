package com.catalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables Spring Data JPA auditing.
 *
 * This is required for @CreatedDate and @LastModifiedDate
 * in BaseEntity to work automatically.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    /*
     * No methods are needed here.
     *
     * The presence of @EnableJpaAuditing is enough to activate:
     * - @CreatedDate
     * - @LastModifiedDate
     */
}