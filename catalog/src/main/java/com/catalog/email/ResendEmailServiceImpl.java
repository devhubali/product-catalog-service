package com.catalog.email;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Sends transactional emails using the Resend API.
 *
 * The API key and base URL are injected from application properties so
 * they can differ between dev and production environments without code changes.
 */
@Service
public class ResendEmailServiceImpl implements EmailService {

    private final Resend resend;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${resend.from-address}")
    private String fromAddress;

    /**
     * Initialises the Resend client with the API key from application properties.
     *
     * @param apiKey The Resend API key injected from {@code resend.api-key} property.
     */
    public ResendEmailServiceImpl(@Value("${resend.api-key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    /**
     * Sends an email containing a verification link to the given address.
     *
     * The link points to {@code GET /api/v1/auth/verify-email?token=<token>}.
     *
     * @param to    The recipient's email address.
     * @param token The one-time verification token.
     */
    @Override
    public void sendVerificationEmail(String to, String token) {
        String verificationLink = baseUrl + "/api/v1/auth/verify-email?token=" + token;

        String html = "<h2>Verify your email</h2>"
                + "<p>Click the link below to verify your account:</p>"
                + "<a href=\"" + verificationLink + "\">Verify Email</a>"
                + "<p>This link does not expire.</p>";

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromAddress)
                .to(to)
                .subject("Verify your email address")
                .html(html)
                .build();

        try {
            resend.emails().send(options);
        } catch (ResendException e) {
            throw new RuntimeException("Failed to send verification email to " + to, e);
        }
    }

    /**
     * Sends an email containing a password reset link to the given address.
     *
     * The link points to {@code POST /api/v1/auth/reset-password} and the token
     * expires in 15 minutes.
     *
     * @param to    The recipient's email address.
     * @param token The one-time password reset token.
     */
    @Override
    public void sendPasswordResetEmail(String to, String token) {
        String resetLink = baseUrl + "/api/v1/auth/reset-password?token=" + token;

        String html = "<h2>Reset your password</h2>"
                + "<p>Click the link below to reset your password. This link expires in 15 minutes.</p>"
                + "<a href=\"" + resetLink + "\">Reset Password</a>"
                + "<p>If you did not request a password reset, ignore this email.</p>";

        CreateEmailOptions options = CreateEmailOptions.builder()
                .from(fromAddress)
                .to(to)
                .subject("Reset your password")
                .html(html)
                .build();

        try {
            resend.emails().send(options);
        } catch (ResendException e) {
            throw new RuntimeException("Failed to send password reset email to " + to, e);
        }
    }
}
