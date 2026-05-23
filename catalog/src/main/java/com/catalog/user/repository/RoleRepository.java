package com.catalog.user.repository;

import com.catalog.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Database access layer for Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Finds a role by its name (e.g. "ROLE_USER" or "ROLE_ADMIN").
     *
     * Used when assigning roles to new users during registration.
     */
    Optional<Role> findByName(String name);

    /**
     * Returns true if a role with this name already exists.
     *
     * Used in the data seeder to avoid creating duplicate roles.
     */
    boolean existsByName(String name);
}
