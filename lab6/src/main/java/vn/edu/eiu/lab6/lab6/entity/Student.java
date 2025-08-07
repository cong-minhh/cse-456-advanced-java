package vn.edu.eiu.lab6.lab6.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private int yearOfBirth;
    
    @Column(nullable = false)
    private double gpa;
}
