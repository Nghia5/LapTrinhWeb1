package studenmanager.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import studenmanager.demo.model.Lecturer;
import studenmanager.demo.repository.LecturerRepository;

@RestController
@RequestMapping("/api/lecturers") // Đổi đường dẫn API thành lecturers
public class LecturerRestController {

    @Autowired
    private LecturerRepository lecturerRepository;

    // API 1: Lấy danh sách
    @GetMapping
    public List<Lecturer> getAll() {
        return lecturerRepository.findAll();
    }

    // API 2: Lấy chi tiết
    @GetMapping("/{id}")
    public Optional<Lecturer> getOne(@PathVariable UUID id) {
        return lecturerRepository.findById(id);
    }

    // API 3: Thêm mới
    @PostMapping
    public Lecturer create(@RequestBody Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    // API 4: Cập nhật
    @PostMapping("/update/{id}")
    public Lecturer update(@PathVariable UUID id, @RequestBody Lecturer lecturer) {
        lecturer.setId(id); // Đảm bảo ID đúng là UUID
        return lecturerRepository.save(lecturer);
    }

    // API 5: Xóa
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        lecturerRepository.deleteById(id);
        return "Deleted lecturer " + id;
    }
}