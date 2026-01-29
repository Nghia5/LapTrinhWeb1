package studenmanager.demo.service; // 1. Kiểm tra kỹ dòng này

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; // 2. Phải có cái này

import studenmanager.demo.model.User;
import studenmanager.demo.repository.UserRepository;

@Service // 3. BẮT BUỘC PHẢI CÓ DÒNG NÀY ĐỂ MÁY NHẬN DIỆN
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // Lưu ý: Database phải lưu là "ADMIN" hoặc "USER"
                .build();
    }
}