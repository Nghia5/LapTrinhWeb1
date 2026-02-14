package studenmanager.demo.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "lecturers") // Map với bảng lecturers
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "lecturer_code")
    private String lecturerCode;

    @Column(name = "full_name")
    private String fullName;

    private String email;
    private String phone;
    
    // Để đơn giản cho bài tập, ta tạm thời map ID của khoa và chức danh
    @Column(name = "department_id")
    private UUID departmentId;

    @Column(name = "position_id")
    private UUID positionId;

    // Constructor
    public Lecturer() {}

    // Getter & Setter (Bạn tự generate hoặc copy đoạn này)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getLecturerCode() { return lecturerCode; }
    public void setLecturerCode(String lecturerCode) { this.lecturerCode = lecturerCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public UUID getDepartmentId() { return departmentId; }
    public void setDepartmentId(UUID departmentId) { this.departmentId = departmentId; }
    public UUID getPositionId() { return positionId; }
    public void setPositionId(UUID positionId) { this.positionId = positionId; }
}