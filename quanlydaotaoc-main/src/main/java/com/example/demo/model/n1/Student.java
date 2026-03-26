package com.example.demo.model.n1;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    
    // 1. Sửa UUID thành Long để khớp với kiểu BIGINT IDENTITY trong SQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. Ép Spring Boot ánh xạ biến này vào đúng cột "full_name" trong SQL
    @Column(name = "full_name", length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    // ===== Constructor =====
    public Student() {}

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // ===== Getter Setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}