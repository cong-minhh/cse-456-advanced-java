package vn.edu.eiu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class School {
    @Id
    @Column(name = "school_id", length = 3, nullable = false)
    private String schoolId;

    @Column(name = "school_name", length = 100, nullable = false)
    private String schoolName;

    @Column(length = 100)
    private String location;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Major> majors = new HashSet<>();

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Student> students = new HashSet<>();

    @Builder
    public School(String schoolId, String schoolName, String location) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.location = location;
    }

    // Helper methods for bidirectional relationship
    public void addMajor(Major major) {
        majors.add(major);
        major.setSchool(this);
    }

    public void removeMajor(Major major) {
        majors.remove(major);
        major.setSchool(null);
    }

    public void addStudent(Student student) {
        students.add(student);
        student.setSchool(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setSchool(null);
    }
}
