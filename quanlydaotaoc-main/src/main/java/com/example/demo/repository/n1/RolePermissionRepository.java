package com.example.demo.repository.n1;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.n1.RolePermission;
import com.example.demo.model.n1.RolePermissionId;

public interface RolePermissionRepository 
        extends JpaRepository<RolePermission, RolePermissionId> {

    List<RolePermission> findByRoleId(UUID roleId);

    void deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
}