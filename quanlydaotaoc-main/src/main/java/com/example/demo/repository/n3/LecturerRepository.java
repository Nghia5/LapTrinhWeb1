package com.example.demo.repository.n3;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.n3.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {

    // 1. Tìm kiếm theo Tên hoặc Email hoặc Mã giảng viên (Không phân biệt hoa thường)
    // Dùng cho cả Giao diện Web và API
    List<Lecturer> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrLecturerCodeContainingIgnoreCase(
            String fullName, String email, String lecturerCode);

    // 2. Tìm kiếm nâng cao kết hợp với trạng thái hoạt động (Nếu bạn dùng Xóa mềm)
    List<Lecturer> findByIsActiveTrueAndFullNameContainingIgnoreCase(String fullName);
}