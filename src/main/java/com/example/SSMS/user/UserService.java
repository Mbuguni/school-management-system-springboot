package com.example.SSMS.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User registerParentUser(String email, String rawPassword) {
        System.out.println("registerParentUser"+email);
        if (userRepository.findByEmail(email) != null) throw new RuntimeException("User exists");
        User user = new User(email, passwordEncoder.encode(rawPassword), User.Role.PARENT);
        return userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    // For admin/teacher creation, similar methods can be added, called by Admin only.
}