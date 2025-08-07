package vn.edu.eiu.lab6.lab6.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "majors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long majorId;
    
    @Column(nullable = false, unique = true)
    private String majorName;
    
    @Column(length = 500)
    private String description;
}