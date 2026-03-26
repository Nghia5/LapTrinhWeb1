package com.example.demo.service.n1;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.n1.Student;
import com.example.demo.repository.n1.StudentRepository;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> getAll() {
        return repo.findAll();
    }
    
    // 1. Đổi UUID thành Long
    public Student getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Student create(Student student) {
        // Bây giờ ID sẽ do Database tự sinh (Identity), không còn tự sinh UUID ở đây nữa
        return repo.save(student);
    }

    // 2. Đổi UUID thành Long
    public Student update(Long id, Student student) {
        Student old = getById(id);
        if (old == null) return null;

        old.setName(student.getName());
        old.setEmail(student.getEmail());

        return repo.save(old);
    }

    // 3. Đổi UUID thành Long
    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Student> search(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }
}