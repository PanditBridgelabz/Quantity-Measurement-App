package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Set<String> DEFAULT_ROLES = Set.of("ROLE_USER");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createOrUpdateUser(String email, String name, String provider) {
        Instant now = Instant.now();
        return userRepository.findByEmail(email)
                .map(existing -> {
                    boolean changed = false;
                    if (name != null && !name.equals(existing.getName())) {
                        existing.setName(name);
                        changed = true;
                    }
                    if (provider != null && !provider.equals(existing.getProvider())) {
                        existing.setProvider(provider);
                        changed = true;
                    }
                    if (existing.getRoles() == null || existing.getRoles().isEmpty()) {
                        existing.setRoles(DEFAULT_ROLES);
                        changed = true;
                    }
                    if (changed) {
                        existing.setUpdatedAt(now);
                        return userRepository.save(existing);
                    }
                    return existing;
                })
                .orElseGet(() -> {
                    User u = new User();
                    u.setEmail(email);
                    u.setName(name);
                    u.setProvider(provider == null ? "google" : provider);
                    u.setRoles(DEFAULT_ROLES);
                    u.setCreatedAt(now);
                    u.setUpdatedAt(now);
                    return userRepository.save(u);
                });
    }
}
