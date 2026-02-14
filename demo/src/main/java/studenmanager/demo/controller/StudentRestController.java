package studenmanager.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // 1. QUAN TRỌNG: Import thư viện UUID

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import studenmanager.demo.model.Student;
import studenmanager.demo.repository.StudentRepository;

@RestController
@RequestMapping("/api/students") 
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    // API 1: Lấy danh sách
    @GetMapping
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    // API 2: Lấy chi tiết (SỬA LỖI TẠI ĐÂY)
    @GetMapping("/{id}")
    public Optional<Student> getOne(@PathVariable UUID id) { // Đổi Integer -> UUID
        return studentRepository.findById(id);
    }

    // API 3: Thêm mới
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // API 4: Cập nhật (SỬA LỖI TẠI ĐÂY)
    @PostMapping("/update/{id}")
    public Student update(@PathVariable UUID id, @RequestBody Student student) { // Đổi Integer -> UUID
        student.setId(id); // Model Student phải có hàm setId nhận vào UUID
        return studentRepository.save(student);
    }

    // API 5: Xóa (SỬA LỖI TẠI ĐÂY)
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) { // Đổi Integer -> UUID
        studentRepository.deleteById(id);
        return "Deleted student " + id;
    }
}