package vn.edu.eiu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "majors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Major {
    @Id
    @Column(name = "major_id", length = 4, nullable = false)
    private String majorId;

    @Column(name = "major_name", length = 100, nullable = false)
    private String majorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    @ToString.Exclude
    private School school;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    private Set<Student> students = new HashSet<>();

    @Builder
    public Major(String majorId, String majorName, School school) {
        this.majorId = majorId;
        this.majorName = majorName;
        this.school = school;
    }

    // Helper methods for bidirectional relationship
    public void addStudent(Student student) {
        students.add(student);
        student.setMajor(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setMajor(null);
    }
}
