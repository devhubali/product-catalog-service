package com.catalog.user.repository;

import com.catalog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Database access layer for User entities.
 *
 * Spring Data JPA generates the SQL for all these methods automatically
 * based on the method names.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Finds a user by their email address.
     *
     * Used during login and when fetching the current user.
     */
    Optional<User> findByEmail(String email);

    /**
     * Returns true if a user with this email already exists.
     *
     * Used during registration to prevent duplicate accounts.
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email verification token.
     *
     * Used when processing the verify-email link.
     */
    Optional<User> findByEmailVerificationToken(String token);

    /**
     * Finds a user by their password reset token.
     *
     * Used when processing the reset-password request.
     */
    Optional<User> findByPasswordResetToken(String token);
}
