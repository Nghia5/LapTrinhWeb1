package studenmanager.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // Cho phép truy cập file tĩnh, trang đăng ký VÀ trang đăng nhập
                .requestMatchers("/css/**", "/js/**", "/register", "/login").permitAll()
                // Chỉ ADMIN mới được thêm/sửa/xóa sinh viên
                .requestMatchers("/students/new", "/students/edit/**", "/students/delete/**", "/students/save").hasRole("ADMIN")
                // Các request còn lại phải đăng nhập
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                // QUAN TRỌNG: Thêm lại dòng này để dùng file login.html của bạn
                .loginPage("/login")  
                .loginProcessingUrl("/login") // Xử lý khi bấm nút Submit
                .defaultSuccessUrl("/students", true) // Đăng nhập xong thì chuyển về đây
                .failureUrl("/login?error=true") // Đăng nhập sai thì về lại login kèm lỗi
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true") // Đăng xuất xong về lại login
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception { 
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        
        return authenticationManagerBuilder.build();
    }
}