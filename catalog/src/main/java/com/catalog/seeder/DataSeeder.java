package com.catalog.seeder;

import com.catalog.user.entity.Role;
import com.catalog.user.entity.User;
import com.catalog.user.enums.UserStatus;
import com.catalog.user.repository.RoleRepository;
import com.catalog.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Runs once at application startup to seed required initial data.
 *
 * Seeds:
 * 1. ROLE_USER  - assigned to all newly registered users
 * 2. ROLE_ADMIN - assigned to the default admin account
 * 3. One default admin user (admin@catalog.com / admin123)
 *
 * This class is safe to run multiple times. It checks for existing data
 * before inserting, so it will never create duplicates.
 */
@Component
public class DataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedRoles();
        seedAdminUser();
    }

    /**
     * Creates ROLE_USER and ROLE_ADMIN if they do not already exist.
     */
    private void seedRoles() {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role(roleName);
            roleRepository.save(role);
            log.info("Seeded role: {}", roleName);
        }
    }

    /**
     * Creates the default admin user if one does not already exist.
     *
     * Default credentials:
     * Email:    admin@catalog.com
     * Password: admin123
     *
     * Change these credentials immediately in a real environment.
     */
    private void seedAdminUser() {
        String adminEmail = "admin@catalog.com";

        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found. Roles must be seeded first."));

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setStatus(UserStatus.ACTIVE);
        admin.getRoles().add(adminRole);

        userRepository.save(admin);
        log.info("Seeded admin user: {}", adminEmail);
    }
}
