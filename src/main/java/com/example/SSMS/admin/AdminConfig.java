package com.example.SSMS.admin;

import com.example.SSMS.user.User;
import com.example.SSMS.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminConfig {

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@gmail.com";
            if (userRepository.findByEmail(adminEmail) == null) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("123"));
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin user created: " + adminEmail);
            }
        };
    }
}
