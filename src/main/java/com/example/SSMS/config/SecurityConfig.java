package com.example.SSMS.config;

import com.example.SSMS.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return email -> {
            System.out.println("LOGIN ATTEMPT: " + email);
            var user = userRepo.findByEmail(email);
            if (user == null) {
                System.out.println("LOGIN FAIL: User not found for " + email);
                throw new UsernameNotFoundException("User not found");
            }
            System.out.println("LOGIN SUCCESS: Found user " + user.getEmail());
            System.out.println("Password (hashed): " + user.getPassword());
            System.out.println("Role: " + user.getRole().name());
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/parent/**").hasRole("PARENT")
                        .requestMatchers(
                                "/login", "/register",
                                "/css/**", "/js/**", "/images/**", "/static/**",
                                "/style.css", "/script.js", "/index"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email") // match your form
                        .passwordParameter("password")
                        .defaultSuccessUrl("/redirectByRole", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                );
        return http.build();
    }

    // REMOVE this method:
    // @Override
    // public void configure(WebSecurity web) { ... }
}