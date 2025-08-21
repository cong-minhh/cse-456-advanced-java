package vn.edu.eiu.testlab.fmecse456_2131200085.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment")
@Data
@NoArgsConstructor
public class Equipment {
    @Id
    @Column(name = "equipment_id", length = 10)
    private String equipmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_type_id", nullable = false)
    @NotNull(message = "Equipment type is required")
    private EquipmentType equipmentType;

    @Column(name = "equipment_name", nullable = false, length = 150)
    @NotBlank(message = "Equipment name is required")
    @Size(min = 5, max = 100, message = "Equipment name must be between 5 and 100 characters")
    private String equipmentName;

    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "1000.00", message = "Purchase price must be at least 1000.00")
    private BigDecimal purchasePrice;

    @Column(name = "quantity_available", nullable = false)
    @NotNull(message = "Quantity available is required")
    private Integer quantityAvailable;

    @Column(name = "purchase_date", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime purchaseDate;
}
