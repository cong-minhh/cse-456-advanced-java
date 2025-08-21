package vn.edu.eiu.testlab.fmecse456_2131200085.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipment_type")
@Data
@NoArgsConstructor
public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_type_id")
    private Integer equipmentTypeId;

    @Column(name = "type_name", nullable = false, unique = true, length = 100)
    private String typeName;

    @Column(name = "description", length = 255)
    private String description;

    @OneToMany(mappedBy = "equipmentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipment> equipmentList = new ArrayList<>();
    
    @Transient
    private List<Equipment> topEquipment = new ArrayList<>();
    
    public void setTopEquipment(List<Equipment> topEquipment) {
        this.topEquipment = topEquipment != null ? topEquipment : new ArrayList<>();
    }
    
    public List<Equipment> getTopEquipment() {
        return topEquipment != null ? topEquipment : new ArrayList<>();
    }
}
