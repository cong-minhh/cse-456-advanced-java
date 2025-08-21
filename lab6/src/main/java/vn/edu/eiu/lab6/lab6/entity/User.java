package vn.edu.eiu.lab6.lab6.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 255, message = "Password must be at least 6 characters")
    @Column(nullable = false, length = 255)
    private String password;
    
    @NotNull(message = "Role cannot be blank")
    @Min(value = 0, message = "Role must be a valid number")
    @Max(value = 2, message = "Role must be between 0 and 2 (0=User, 1=Admin, 2=Moderator)")
    @Column(nullable = false)
    private Integer role;
}