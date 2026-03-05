package studenmanager.demo.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import studenmanager.demo.model.Department;
import studenmanager.demo.repository.DepartmentRepository;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentRestController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // 1. GET - Lấy danh sách (Có phân trang)
    @GetMapping
    public ResponseEntity<Page<Department>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        // Lưu ý: Nên tạo hàm findByIsActiveTrue trong Repository để lọc dữ liệu sạch
        Page<Department> departments = departmentRepository.findAll(pageable);
        return ResponseEntity.ok(departments);
    }

    // 2. GET - Lấy chi tiết
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable UUID id) {
        return departmentRepository.findById(id)
                .filter(Department::getIsActive)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST - Tạo mới (STT 6 trong bảng thiết kế)
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        department.setId(null);
        department.setIsActive(true);
        // department.setCreatedAt(LocalDateTime.now()); // Nếu bạn đã thêm trường này vào Model
        return ResponseEntity.ok(departmentRepository.save(department));
    }

    // 4. PUT - Cập nhật toàn bộ (STT 7 trong bảng thiết kế)
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable UUID id, @RequestBody Department details) {
        return departmentRepository.findById(id).map(dept -> {
            dept.setCode(details.getCode());
            dept.setName(details.getName());
            dept.setDescription(details.getDescription());
            dept.setEstablishedYear(details.getEstablishedYear());
            // dept.setUpdatedAt(LocalDateTime.now()); 
            
            return ResponseEntity.ok(departmentRepository.save(dept));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. PATCH - Cập nhật một phần
    @PatchMapping("/{id}")
    public ResponseEntity<Department> patchDepartment(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        return departmentRepository.findById(id).map(dept -> {
            if (updates.containsKey("code")) dept.setCode((String) updates.get("code"));
            if (updates.containsKey("name")) dept.setName((String) updates.get("name"));
            if (updates.containsKey("description")) dept.setDescription((String) updates.get("description"));
            if (updates.containsKey("establishedYear")) dept.setEstablishedYear((Integer) updates.get("establishedYear"));
            
            // dept.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(departmentRepository.save(dept));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 6. DELETE - Xóa mềm (STT 11, 12 trong bảng thiết kế)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID id) {
        return departmentRepository.findById(id).map(dept -> {
            dept.setIsActive(false);
            // dept.setDeletedAt(LocalDateTime.now());
            departmentRepository.save(dept);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}