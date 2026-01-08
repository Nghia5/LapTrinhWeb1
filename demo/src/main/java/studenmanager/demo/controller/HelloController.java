package studenmanager.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import studenmanager.demo.model.Student;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot API";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return "Xin chào " + name;
    }

    @GetMapping("/students/search")
    public String search(@RequestParam String keyword,
                        @RequestParam(defaultValue = "1") int page){
        return "keyword = "  + keyword + ", page =" + page;
    }

    @GetMapping("/student/{id}")
    public String getStudent(@PathVariable int id) {
        return "Sinh vien co ma :" + id;
    }

    // --- BÀI 4: ĐƯỜNG DẪN LÀ /student ---
    @GetMapping("/student")
    public Student getStudentInfo() {
        return new Student(1,"Nguyen Trung Nghia",20);
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "A", 20));
        list.add(new Student(2, "B", 21));
        return list;
    }
}