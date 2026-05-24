package com.catalog.email;

/**
 * Contract for sending transactional emails.
 *
 * Using an interface here decouples the rest of the application from the
 * specific email provider (Resend), making it easy to swap providers or
 * mock in tests.
 */
public interface EmailService {

    /**
     * Sends an email verification link to the given address.
     *
     * @param to    The recipient's email address.
     * @param token The one-time verification token to include in the link.
     */
    void sendVerificationEmail(String to, String token);

    /**
     * Sends a password reset link to the given address.
     *
     * @param to    The recipient's email address.
     * @param token The one-time password reset token to include in the link.
     */
    void sendPasswordResetEmail(String to, String token);
}
