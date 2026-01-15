package studenmanager.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import studenmanager.demo.model.Student;
import studenmanager.demo.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/api/students-json") // Đây chính là cái biển số nhà "/students"
    public String listStudents(Model model) {
        List<Student> list = studentRepository.findAll();
        model.addAttribute("students", list);
        return "students"; // Trả về giao diện students.html
    }
}