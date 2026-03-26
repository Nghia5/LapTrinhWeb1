package com.example.demo.service.n1;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.model.n1.RolePermission;
import com.example.demo.repository.n1.RolePermissionRepository;

@Service
public class RolePermissionService {

    private final RolePermissionRepository repository;

    public RolePermissionService(RolePermissionRepository repository) {
        this.repository = repository;
    }

    public RolePermission assignPermission(UUID roleId, UUID permissionId) {
        RolePermission rp = new RolePermission(roleId, permissionId);
        return repository.save(rp);
    }

    public void removePermission(UUID roleId, UUID permissionId) {
        repository.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    public List<RolePermission> getPermissionsByRoleId(UUID roleId) {
        return repository.findByRoleId(roleId);
    }

}