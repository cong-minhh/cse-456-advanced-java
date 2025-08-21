package vn.edu.eiu.lab6.lab6.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.eiu.lab6.lab6.validation.VietnameseName;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    @VietnameseName(message = "Each word in the name must start with an uppercase letter")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Year of birth cannot be blank")
    @Min(value = 1999, message = "Age must be between 18 and 25 (year of birth between 1999-2006)")
    @Max(value = 2006, message = "Age must be between 18 and 25 (year of birth between 1999-2006)")
    @Column(nullable = false)
    private Integer yearOfBirth;

    @NotNull(message = "GPA cannot be blank")
    @DecimalMin(value = "0.0", message = "GPA must be between 0 and 100")
    @DecimalMax(value = "100.0", message = "GPA must be between 0 and 100")
    @Column(nullable = false)
    private Double gpa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    public Student(String name, Integer yearOfBirth, Double gpa) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.gpa = gpa;
    }
}
