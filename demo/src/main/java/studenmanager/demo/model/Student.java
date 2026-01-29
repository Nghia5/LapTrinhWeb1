package studenmanager.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    
    private String name;
    
    private Integer age; 
    
    private String email;

    private String gender; // Biến giới tính

    // Constructor mặc định (Bắt buộc)
    public Student() {
    }

    // Constructor đầy đủ
    public Student(Integer id, String name, Integer age, String email, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.gender = gender;
    }

    // --- GETTER và SETTER ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // --- PHẦN BẠN ĐANG THIẾU/VIẾT SAI ---
    
    public String getGender() { // Trả về String, không phải Integer
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}