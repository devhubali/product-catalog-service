package com.catalog.auth.service;

import com.catalog.auth.dto.AuthResponse;
import com.catalog.auth.dto.ChangePasswordRequest;
import com.catalog.auth.dto.LoginRequest;
import com.catalog.auth.dto.RegisterRequest;
import com.catalog.auth.dto.UserResponse;
import com.catalog.common.exception.BadRequestException;
import com.catalog.common.exception.DuplicateResourceException;
import com.catalog.common.exception.ResourceNotFoundException;
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

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

        User savedUser = userRepository.save(user);
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
}
