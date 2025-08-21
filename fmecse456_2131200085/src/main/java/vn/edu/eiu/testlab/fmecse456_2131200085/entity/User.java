package vn.edu.eiu.testlab.fmecse456_2131200085.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    public enum Role {
        ADMIN(1), STAFF(2), CUSTOMER(3);
        
        private final int value;
        
        Role(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
        
        public static Role fromValue(int value) {
            for (Role role : values()) {
                if (role.value == value) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid role value: " + value);
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Username is required")
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    @NotBlank(message = "Password is required")
    private String password;
    
    @Column(name = "role", nullable = false)
    @NotNull(message = "Role is required")
    private Integer role;
    
    public Role getRoleEnum() {
        return Role.fromValue(this.role);
    }
    
    public void setRole(Role role) {
        this.role = role.getValue();
    }
}
