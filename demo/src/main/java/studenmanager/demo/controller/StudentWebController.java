package studenmanager.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // <--- QUAN TRỌNG: Phải là @Controller
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import studenmanager.demo.repository.StudentRepository;

@Controller // Nếu dùng @RestController ở đây sẽ bị lỗi hiển thị JSON thay vì web
public class StudentWebController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public String listStudents(Model model) {
        // Lấy list sinh viên
        var list = studentRepository.findAll();
        
        model.addAttribute("students", list);
        
        // Trả về file students.html
        return "students";
    }
}