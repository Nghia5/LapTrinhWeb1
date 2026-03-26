package com.example.demo.service.n3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.model.n1.Role; // Bắt buộc phải import class Role
import com.example.demo.repository.UserRepository;

@Service // BẮT BUỘC PHẢI CÓ DÒNG NÀY ĐỂ MÁY NHẬN DIỆN
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Đã bỏ đoạn ép kiểu (Object) dư thừa đi cho code sạch hơn
        User user = userRepository.findByUsername(username).stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Bước đột phá: Chuyển Set<Role> của bản n1 thành mảng String để Security đọc được
        String[] userRoles = user.getRoles().stream()
                .map(Role::getName) // Lưu ý: Nếu trong file Role.java bạn đặt tên hàm là getRoleName() thì sửa lại chữ getName thành getRoleName nhé
                .toArray(String[]::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(userRoles) // Nạp mảng quyền kiểu mới vào đây
                .build();
    }
}