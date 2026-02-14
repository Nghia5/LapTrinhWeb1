package studenmanager.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import studenmanager.demo.model.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, UUID> {
    // Hàm tìm kiếm theo Tên hoặc Email (Không phân biệt hoa thường)
    List<Lecturer> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}