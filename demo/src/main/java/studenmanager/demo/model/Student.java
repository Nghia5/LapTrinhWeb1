package studenmanager.demo.model;

import java.util.UUID;

import jakarta.persistence.Column; // Import thư viện UUID
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Đổi sang UUID
    private UUID id; // Đổi từ Integer sang UUID
    
    @Column(name = "full_name") // Map với cột full_name trong SQL
    private String name;
    
    private Integer age; 
    private String email;
    private String gender;

    // --- CÁC TRƯỜNG PHỤ TỪ FILE SQL (Nên thêm vào) ---
    @Column(name = "is_active")
    private Boolean isActive = true;

    // Constructor
    public Student() {}

    // Getter & Setter cho ID
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    
    // ... Các getter setter khác giữ nguyên (chỉ đổi String gender như bạn đã sửa)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}