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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import studenmanager.demo.model.Lecturer;
import studenmanager.demo.repository.DepartmentRepository;
import studenmanager.demo.repository.LecturerRepository;
import studenmanager.demo.repository.PositionRepository;

@Controller
@RequestMapping("/lecturers")
public class LecturerController {

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private PositionRepository positionRepository;

    @GetMapping
    public String listLecturers(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        List<Lecturer> lecturers;
        
        if (keyword != null && !keyword.isEmpty()) {
            lecturers = lecturerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrLecturerCodeContainingIgnoreCase(keyword, keyword, keyword);
        } else {
            lecturers = lecturerRepository.findAll();
        }
        
        // Mẹo: Nếu bạn muốn chỉ hiện người chưa bị xóa trên web:
        // lecturers = lecturers.stream().filter(l -> l.getIsActive() == null || l.getIsActive()).toList();
        
        model.addAttribute("lecturers", lecturers);
        model.addAttribute("keyword", keyword);
        return "lecturers";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("lecturer", new Lecturer());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("positions", positionRepository.findAll());
        return "lecturer_form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecturer Id:" + id));
        model.addAttribute("lecturer", lecturer);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("positions", positionRepository.findAll());
        return "lecturer_form";
    }

    @PostMapping("/save")
    public String saveLecturer(@ModelAttribute("lecturer") Lecturer lecturer) {
        if (lecturer.getId() == null) {
            lecturer.setIsActive(true); // Gán mặc định cho giảng viên mới
        } else {
            // Nếu là cập nhật, giữ nguyên trạng thái cũ hoặc gán lại true
            lecturer.setIsActive(true); 
        }
        lecturerRepository.save(lecturer);
        return "redirect:/lecturers";
    }

    // --- Cập nhật: Chuyển sang Xóa mềm (Soft Delete) ---
    @GetMapping("/delete/{id}")
    public String deleteLecturer(@PathVariable("id") UUID id) {
        lecturerRepository.findById(id).ifPresent(l -> {
            l.setIsActive(false); // Đánh dấu là đã xóa
            lecturerRepository.save(l);
        });
        return "redirect:/lecturers";
    }
}