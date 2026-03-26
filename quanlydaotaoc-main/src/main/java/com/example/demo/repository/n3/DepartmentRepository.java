package com.example.demo.repository.n3;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.n3.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}