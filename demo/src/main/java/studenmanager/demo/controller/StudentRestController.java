package studenmanager.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import studenmanager.demo.model.Student;
import studenmanager.demo.repository.StudentRepository;

// 1. Dùng @RestController để Swagger nhận diện đây là API dữ liệu
@RestController
// 2. Định nghĩa đường dẫn bắt đầu là /api/students như trong ảnh
@RequestMapping("/api/students") 
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    // API 1: Lấy danh sách (GET /api/students)
    @GetMapping
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    // API 2: Lấy chi tiết 1 sinh viên (GET /api/students/{id})
    @GetMapping("/{id}")
    public Optional<Student> getOne(@PathVariable Integer id) {
        return studentRepository.findById(id);
    }

    // API 3: Thêm mới (POST /api/students)
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // API 4: Cập nhật (POST /api/students/update/{id} - Giống ảnh bạn gửi)
    // Lưu ý: Đúng chuẩn REST nên dùng @PutMapping, nhưng để giống ảnh mình dùng Post
    @PostMapping("/update/{id}")
    public Student update(@PathVariable Integer id, @RequestBody Student student) {
        student.setId(id); // Giả sử model có hàm setId
        return studentRepository.save(student);
    }

    // API 5: Xóa (POST /api/students/delete/{id} - Giống ảnh bạn gửi)
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        studentRepository.deleteById(id);
        return "Deleted student " + id;
    }
}