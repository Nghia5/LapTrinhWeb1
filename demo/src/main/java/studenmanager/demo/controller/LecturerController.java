package studenmanager.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import studenmanager.demo.model.Lecturer;
import studenmanager.demo.repository.LecturerRepository;

@Controller
public class LecturerController {

    @Autowired
    private LecturerRepository lecturerRepository;

    // 1. HIỂN THỊ DANH SÁCH & TÌM KIẾM
    @GetMapping("/lecturers")
    public String listLecturers(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Lecturer> list;
        
        if (keyword != null && !keyword.isEmpty()) {
            // Gọi hàm tìm kiếm vừa viết trong Repository
            list = lecturerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        } else {
            list = lecturerRepository.findAll();
        }
        
        model.addAttribute("lecturers", list);
        model.addAttribute("keyword", keyword);
        return "lecturers"; // Bạn cần tạo file lecturers.html tương ứng
    }

    // 2. FORM THÊM MỚI
    @GetMapping("/lecturers/new")
    public String showNewForm(Model model) {
        model.addAttribute("lecturer", new Lecturer());
        return "lecturer_form"; // Bạn cần tạo file lecturer_form.html tương ứng
    }

    // 3. LƯU (THÊM HOẶC SỬA)
    @PostMapping("/lecturers/save")
    public String saveLecturer(@ModelAttribute("lecturer") Lecturer lecturer) {
        lecturerRepository.save(lecturer);
        return "redirect:/lecturers";
    }

    // 4. FORM SỬA
    @GetMapping("/lecturers/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        Lecturer lecturer = lecturerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid lecturer Id:" + id));
        
        model.addAttribute("lecturer", lecturer);
        return "lecturer_form";
    }

    // 5. XÓA
    @GetMapping("/lecturers/delete/{id}")
    public String deleteLecturer(@PathVariable("id") UUID id) {
        lecturerRepository.deleteById(id);
        return "redirect:/lecturers";
    }
}