package com.catalog.user.enums;

/**
 * Represents the current status of a user account.
 *
 * Use this instead of raw strings or booleans so the code
 * is safer and easier to extend.
 */
public enum UserStatus {

    /**
     * The user account is active and fully usable.
     */
    ACTIVE,

    /**
     * The user account has been deactivated by an admin or the user themselves.
     */
    INACTIVE,

    /**
     * The user account has been banned and cannot log in.
     */
    BANNED
}
