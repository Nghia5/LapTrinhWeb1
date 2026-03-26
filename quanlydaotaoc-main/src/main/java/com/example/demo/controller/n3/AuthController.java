package com.example.demo.controller.n3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.model.n1.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.n1.RoleRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Trả về file login.html trong thư mục templates
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // SỬA Ở ĐÂY: Tìm quyền ADMIN từ Database và gán cho User
        Role adminRole = (Role) roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            throw new RuntimeException("Không tìm thấy quyền ADMIN trong Database");
        }
        user.getRoles().add(adminRole);
        
        userRepository.save(user);
        return "redirect:/login"; 
    }

    // --- ĐÃ XÓA ĐOẠN @GetMapping("/login") Ở ĐÂY ---
    // Lý do: Để Spring Security tự động hiển thị trang đăng nhập mặc định.
}