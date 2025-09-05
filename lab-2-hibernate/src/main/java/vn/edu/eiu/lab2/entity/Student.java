package vn.edu.eiu.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Students")
@AllArgsConstructor
@NoArgsConstructor

public class Student {
    @Id
    @Column(name = "Id", columnDefinition = "CHAR(10)")

    private String Id;

    @Column(name = "Name", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String name;

    @Column(name = "GPA", nullable = false)
    private double GPA;

    @Column(name = "Year of birth", nullable = false)
    private double Yob;
}
