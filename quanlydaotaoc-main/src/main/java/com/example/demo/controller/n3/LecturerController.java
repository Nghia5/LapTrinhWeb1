package com.example.demo.controller.n3;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.n3.Lecturer;
import com.example.demo.repository.n3.DepartmentRepository;
import com.example.demo.repository.n3.LecturerRepository;
import com.example.demo.repository.n3.PositionRepository;

@Controller
@RequestMapping("/")
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
        
        // Chỉ lấy những giảng viên chưa bị xóa (isActive = true)
        if (keyword != null && !keyword.isEmpty()) {
            lecturers = lecturerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrLecturerCodeContainingIgnoreCase(keyword, keyword, keyword)
                    .stream().filter(l -> l.getIsActive() == null || l.getIsActive()).collect(Collectors.toList());
        } else {
            lecturers = lecturerRepository.findAll()
                    .stream().filter(l -> l.getIsActive() == null || l.getIsActive()).collect(Collectors.toList());
        }
        
        model.addAttribute("lecturers", lecturers);
        model.addAttribute("keyword", keyword);
        return "index"; // Gộp tất cả vào file index.html
    }

    @GetMapping("/lecturers/new")
    public String showCreateForm(Model model) {
        model.addAttribute("lecturer", new Lecturer());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("positions", positionRepository.findAll());
        return "lecturer_form";
    }

    @GetMapping("/lecturers/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid lecturer Id:" + id));
        model.addAttribute("lecturer", lecturer);
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("positions", positionRepository.findAll());
        return "lecturer_form";
    }

    @PostMapping("/lecturers/save")
    public String saveLecturer(@ModelAttribute("lecturer") Lecturer lecturer) {
        lecturer.setIsActive(true); 
        lecturerRepository.save(lecturer);
        // Sau khi lưu, quay về trang chủ và mở tab Giảng viên
        return "redirect:/?tab=gv";
    }

    @GetMapping("/lecturers/delete/{id}")
    public String deleteLecturer(@PathVariable("id") UUID id) {
        lecturerRepository.findById(id).ifPresent(l -> {
            l.setIsActive(false);
            lecturerRepository.save(l);
        });
        // Sau khi xóa, quay về trang chủ và mở tab Giảng viên
        return "redirect:/?tab=gv";
    }
}