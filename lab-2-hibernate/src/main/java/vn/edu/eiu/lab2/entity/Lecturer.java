package vn.edu.eiu.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Lecturer")
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", length = 50, nullable = false)
    private String name;

    @Column(name = "Yob", nullable = false)
    private int yob;

    @Column(name = "Salary", nullable = false)
    private double salary;
}
