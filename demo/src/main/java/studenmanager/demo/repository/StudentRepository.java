package studenmanager.demo.repository;

import java.util.List;
import java.util.UUID; // Import UUID

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import studenmanager.demo.model.Student; 

@Repository
// Đổi <Student, Integer> thành <Student, UUID>
public interface StudentRepository extends JpaRepository<Student, UUID> {
    
    // Tìm kiếm (Lưu ý tên hàm findBy... phải khớp tên biến trong Model)
    // Ở Model mình để là 'name', 'email' nên hàm này đúng.
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}