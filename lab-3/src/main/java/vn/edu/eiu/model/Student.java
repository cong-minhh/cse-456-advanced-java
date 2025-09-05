package vn.edu.eiu.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dob;

    private Double gpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Major major;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private School school;

    @Column(name = "enrollment_year", nullable = false)
    private Integer enrollmentYear;

    @Builder
    public Student(String fullName, Gender gender, LocalDate dob, Double gpa, Major major, School school, Integer enrollmentYear) {
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.gpa = gpa;
        this.major = major;
        this.school = school;
        this.enrollmentYear = enrollmentYear;
    }

    public String getMajorName() {
        return major != null ? major.getMajorName() : null;
    }

    public String getSchoolName() {
        return school != null ? school.getSchoolName() : null;
    }
}
