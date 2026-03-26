package com.example.demo.repository.n1;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.n1.Permission;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
