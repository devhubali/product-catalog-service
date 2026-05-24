package com.catalog.auth.service;

import com.catalog.auth.dto.AuthResponse;
import com.catalog.auth.dto.ChangePasswordRequest;
import com.catalog.auth.dto.ForgotPasswordRequest;
import com.catalog.auth.dto.LoginRequest;
import com.catalog.auth.dto.RegisterRequest;
import com.catalog.auth.dto.ResetPasswordRequest;
import com.catalog.auth.dto.UserResponse;
import com.catalog.common.exception.BadRequestException;
import com.catalog.common.exception.DuplicateResourceException;
import com.catalog.common.exception.ResourceNotFoundException;
import com.catalog.email.EmailService;
import com.catalog.security.JwtService;
import com.catalog.user.entity.Role;
import com.catalog.user.entity.User;
import com.catalog.user.enums.UserStatus;
import com.catalog.user.repository.RoleRepository;
import com.catalog.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Handles the business logic for registration, login, and password management.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    /**
     * Creates a new user account and returns a JWT token.
     *
     * Steps:
     * 1. Check that the email is not already taken.
     * 2. Assign the default ROLE_USER role.
     * 3. Hash the password and save the user.
     * 4. Generate and return a JWT token.
     */
    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw DuplicateResourceException.of("User", "email", request.email());
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role ROLE_USER not found. Make sure the seeder has run."));

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setStatus(UserStatus.ACTIVE);
        user.getRoles().add(userRole);

        // Generate a one-time email verification token
        String verificationToken = UUID.randomUUID().toString();
        user.setEmailVerificationToken(verificationToken);

        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(savedUser.getEmail(), verificationToken);

        String token = jwtService.generateToken(savedUser.getEmail());
        return new AuthResponse(token, UserResponse.from(savedUser));
    }

    /**
     * Authenticates a user with email and password.
     *
     * Spring Security's AuthenticationManager validates the credentials.
     * If they are wrong, it throws BadCredentialsException automatically.
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.email()));

        // Block login until the user has verified their email address
        if (!user.isEmailVerified()) {
            throw new BadRequestException("Email address not verified. Please check your inbox for the verification link.");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, UserResponse.from(user));
    }

    /**
     * Returns the profile of the currently authenticated user.
     *
     * The email comes from the JWT token via the authentication context.
     */
    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return UserResponse.from(user);
    }

    /**
     * Changes the user's password after verifying the current one.
     */
    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    /**
     * Marks the user's email as verified and clears the verification token.
     *
     * Throws {@link BadRequestException} if the token is invalid or not found.
     *
     * @param token The one-time verification token from the email link.
     */
    @Override
    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid or expired verification token."));

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        userRepository.save(user);
    }

    /**
     * Generates a password reset token, saves it with a 15-minute expiry,
     * and sends a reset link to the user's email.
     *
     * If no account is found for the given email, the request silently succeeds
     * to avoid leaking whether an account exists.
     *
     * @param request Contains the email address of the account to recover.
     */
    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        userRepository.findByEmail(request.email()).ifPresent(user -> {
            String resetToken = UUID.randomUUID().toString();
            user.setPasswordResetToken(resetToken);
            user.setPasswordResetTokenExpiresAt(LocalDateTime.now().plusMinutes(15));
            userRepository.save(user);
            emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
        });
    }

    /**
     * Validates the reset token and updates the user's password.
     *
     * Throws {@link BadRequestException} if the token is invalid or has expired.
     *
     * @param request Contains the reset token and the new password.
     */
    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByPasswordResetToken(request.token())
                .orElseThrow(() -> new BadRequestException("Invalid or expired password reset token."));

        if (user.getPasswordResetTokenExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Password reset token has expired. Please request a new one.");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setPasswordResetToken(null);
        user.setPasswordResetTokenExpiresAt(null);
        userRepository.save(user);
    }
}
