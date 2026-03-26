package com.example.demo.repository.n1;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.n1.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    public Object findByName(String admin);
}
