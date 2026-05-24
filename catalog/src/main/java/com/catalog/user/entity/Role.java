package com.catalog.user.entity;

import com.catalog.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents a permission role that can be assigned to users.
 *
 * Role names follow the Spring Security convention: ROLE_USER, ROLE_ADMIN.
 * Using the "ROLE_" prefix lets Spring Security match roles automatically.
 */
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{name='" + name + "'}";
    }
}
