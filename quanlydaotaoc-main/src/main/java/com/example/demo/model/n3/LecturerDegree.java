package com.example.demo.model.n3;

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
@Table(name = "lecturer_degrees")
public class LecturerDegree {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    private String degree;
    private String major;
    private String university;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @Column(name = "is_highest")
    private Integer isHighest;

    public LecturerDegree() {}
    // Generate Getter/Setter sau
}