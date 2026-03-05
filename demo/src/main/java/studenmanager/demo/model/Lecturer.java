package studenmanager.demo.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lecturers")
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "lecturer_code")
    private String lecturerCode;

    @Column(name = "full_name")
    private String fullName;

    // --- CÁC TRƯỜNG BẠN ĐANG THIẾU ---
    private Integer gender; // 0: Nữ, 1: Nam

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth; // Ngày sinh

    private String email;
    private String phone;

    @Column(name = "hire_date")
    private LocalDate hireDate; // Ngày vào làm

    @Column(name = "is_active")
    private Boolean isActive = true;

    // --- LIÊN KẾT BẢNG ---
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    public Lecturer() {}

    // --- GETTER & SETTER ĐẦY ĐỦ ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getLecturerCode() { return lecturerCode; }
    public void setLecturerCode(String lecturerCode) { this.lecturerCode = lecturerCode; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Integer getGender() { return gender; }
    public void setGender(Integer gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
}