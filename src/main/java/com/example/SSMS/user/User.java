package com.example.SSMS.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email; // Used for login
    private String password; // BCrypt encoded

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN, TEACHER, PARENT
    }

    // Constructors, getters, and setters
    public User() {}
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}