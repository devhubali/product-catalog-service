package com.catalog.auth.dto;

import com.catalog.user.entity.Role;
import com.catalog.user.entity.User;
import com.catalog.user.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Response body returned when the client needs user information.
 *
 * We never return the User entity directly because it contains
 * sensitive fields like the hashed password.
 */
public record UserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        UserStatus status,
        Set<String> roles,
        LocalDateTime createdAt
) {

    /**
     * Converts a User entity into a safe response object.
     *
     * The password and other internal fields are not included.
     */
    public static UserResponse from(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus(),
                roleNames,
                user.getCreatedAt()
        );
    }
}
