package studenmanager.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Import cái này
import org.springframework.stereotype.Repository; // Import cái này

import studenmanager.demo.model.Student; // Quan trọng: Phải import class Student từ gói model sang

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}