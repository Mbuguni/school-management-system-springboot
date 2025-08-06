package com.example.SSMS.parent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByUserEmail(String email);
    boolean existsByUserEmail(String email);
}