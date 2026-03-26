package com.example.demo.repository.n1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.n1.Student;

// 2. Đổi UUID thành Long ở đây
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCase(String name);
}