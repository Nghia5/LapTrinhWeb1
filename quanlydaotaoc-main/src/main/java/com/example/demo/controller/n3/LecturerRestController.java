package com.example.demo.controller.n3;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.model.n3.Lecturer;
import com.example.demo.repository.n3.LecturerRepository;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturerRestController {

    @Autowired
    private LecturerRepository lecturerRepository;

    // 1. GET /api/v1/lecturers - Lấy danh sách (Có lọc cơ bản)
    @GetMapping
    public ResponseEntity<List<Lecturer>> getAllLecturers(
            @RequestParam(required = false) String keyword) {
        
        List<Lecturer> lecturers = lecturerRepository.findAll();
        
        // Lọc những người còn hoạt động (isActive = true)
        List<Lecturer> activeLecturers = lecturers.stream()
                .filter(l -> l.getIsActive() == null || l.getIsActive())
                .collect(Collectors.toList());

        // Nếu có keyword thì lọc theo tên hoặc email
        if (keyword != null && !keyword.isEmpty()) {
            activeLecturers = activeLecturers.stream()
                    .filter(l -> l.getFullName().toLowerCase().contains(keyword.toLowerCase()) 
                              || l.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return ResponseEntity.ok(activeLecturers);
    }

    // 2. GET /api/v1/lecturers/{id} - Lấy chi tiết
    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable UUID id) {
        return lecturerRepository.findById(id)
                .filter(l -> l.getIsActive() == null || l.getIsActive())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. POST /api/v1/lecturers - Tạo mới
    @PostMapping
    public ResponseEntity<Lecturer> createLecturer(@RequestBody Lecturer lecturer) {
        lecturer.setId(null);
        lecturer.setIsActive(true);
        return ResponseEntity.ok(lecturerRepository.save(lecturer));
    }

    // 4. PUT /api/v1/lecturers/{id} - Cập nhật toàn bộ
    @PutMapping("/{id}")
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable UUID id, @RequestBody Lecturer details) {
        return lecturerRepository.findById(id).map(l -> {
            l.setLecturerCode(details.getLecturerCode());
            l.setFullName(details.getFullName());
            l.setEmail(details.getEmail());
            l.setPhone(details.getPhone());
            l.setDepartment(details.getDepartment());
            l.setPosition(details.getPosition());
            return ResponseEntity.ok(lecturerRepository.save(l));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. PATCH /api/v1/lecturers/{id} - Cập nhật một phần (MỚI)
    @PatchMapping("/{id}")
    public ResponseEntity<Lecturer> patchLecturer(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        return lecturerRepository.findById(id).map(l -> {
            if (updates.containsKey("fullName")) l.setFullName((String) updates.get("fullName"));
            if (updates.containsKey("email")) l.setEmail((String) updates.get("email"));
            if (updates.containsKey("phone")) l.setPhone((String) updates.get("phone"));
            return ResponseEntity.ok(lecturerRepository.save(l));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 6. DELETE /api/v1/lecturers/{id} - Xóa mềm (is_active = 0)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable UUID id) {
        return lecturerRepository.findById(id).map(l -> {
            l.setIsActive(false); // Xóa mềm theo yêu cầu nhóm
            lecturerRepository.save(l);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}