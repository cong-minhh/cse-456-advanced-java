package vn.edu.eiu.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Subject")
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    @Column(name = "Code", columnDefinition = "CHAR(10)")
    private String code;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @Column(name = "Description", length = 255)
    private String description;

    @Column(name = "Credits", nullable = false)
    private int credits;

    @Column(name = "StudyHours", nullable = false)
    private int studyHours;
}
