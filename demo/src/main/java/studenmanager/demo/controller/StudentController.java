package studenmanager.demo.controller;

import java.util.List;
import java.util.UUID; // 1. QUAN TRỌNG: Phải import thư viện này

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
    @GetMapping("/students")
    public String listStudents(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Student> list;
        
        if (keyword != null && !keyword.isEmpty()) {
            list = studentRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        } else {
            list = studentRepository.findAll();
        }
        
        model.addAttribute("students", list);
        model.addAttribute("keyword", keyword);
        return "students";
    }

    // 2. CHỨC NĂNG MỞ FORM THÊM MỚI
    @GetMapping("/students/new")
    public String showNewForm(Model model) {
        model.addAttribute("student", new Student());
        return "student_form";
    }

    // 3. CHỨC NĂNG LƯU
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    // 4. CHỨC NĂNG MỞ FORM SỬA (ĐÃ SỬA LỖI TẠI ĐÂY)
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) { // Đổi Integer -> UUID
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        
        model.addAttribute("student", student);
        return "student_form";
    }

    // 5. CHỨC NĂNG XÓA (ĐÃ SỬA LỖI TẠI ĐÂY)
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") UUID id) { // Đổi Integer -> UUID
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}