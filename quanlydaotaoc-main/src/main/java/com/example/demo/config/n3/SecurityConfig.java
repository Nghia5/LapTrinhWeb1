package com.example.demo.config.n3;

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
        .csrf(csrf -> csrf.disable()) // Tắt cái này để Form Đăng ký/Đăng nhập chạy được
        .authorizeHttpRequests((requests) -> requests
            // Cho phép tất cả mọi người vào các trang này mà không cần đăng nhập
            .requestMatchers("/css/**", "/js/**", "/images/**", "/register", "/login").permitAll() 
            .anyRequest().authenticated() // Các trang còn lại bắt buộc phải đăng nhập
        )
        .formLogin((form) -> form
            .loginPage("/login")        
            .loginProcessingUrl("/login") 
            .defaultSuccessUrl("/", true) 
            .permitAll() // <<< CỰC KỲ QUAN TRỌNG: Phải có dòng này ở đây
        )
        .logout((logout) -> logout.permitAll());

    return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception { 
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        
        return authenticationManagerBuilder.build();
    }
}