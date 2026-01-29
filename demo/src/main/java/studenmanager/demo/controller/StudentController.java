package studenmanager.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import studenmanager.demo.model.Student;
import studenmanager.demo.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // 1. CHỨC NĂNG HIỂN THỊ DANH SÁCH & TÌM KIẾM
    @GetMapping("/students") // Đã sửa lại đường dẫn cho khớp với HTML
    public String listStudents(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Student> list;
        
        if (keyword != null && !keyword.isEmpty()) {
            // Nếu có từ khóa tìm kiếm -> Gọi hàm tìm kiếm bên Repository
            // Lưu ý: Bạn cần thêm hàm này vào file StudentRepository interface nhé!
            list = studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        } else {
            // Nếu không tìm kiếm -> Lấy tất cả
            list = studentRepository.findAll();
        }
        
        model.addAttribute("students", list);
        model.addAttribute("keyword", keyword); // Truyền lại từ khóa ra view để giữ lại trong ô input
        return "students"; // Trả về file students.html
    }

    // 2. CHỨC NĂNG MỞ FORM THÊM MỚI
    @GetMapping("/students/new")
    public String showNewForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form"; // Trả về file student_form.html (Bạn cần tạo file này)
    }

    // 3. CHỨC NĂNG LƯU (Dùng cho cả Thêm mới và Sửa)
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentRepository.save(student);
        return "redirect:/students"; // Lưu xong thì quay về danh sách
    }

    // 4. CHỨC NĂNG MỞ FORM SỬA (Lấy dữ liệu cũ lên)
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        
        model.addAttribute("student", student);
        return "student_form"; // Tái sử dụng form thêm mới
    }

    // 5. CHỨC NĂNG XÓA
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}