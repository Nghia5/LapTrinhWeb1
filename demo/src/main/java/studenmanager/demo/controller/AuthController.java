package studenmanager.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import studenmanager.demo.model.User;
import studenmanager.demo.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Mặc định đăng ký xong là USER thường
        user.setRole("USER"); 
        
        userRepository.save(user);
        return "redirect:/login"; // Đã sửa: bỏ "?success" để về trang login mặc định gọn hơn
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
    // --- ĐÃ XÓA ĐOẠN @GetMapping("/login") Ở ĐÂY ---
    // Lý do: Để Spring Security tự động hiển thị trang đăng nhập mặc định.