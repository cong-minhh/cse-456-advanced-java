package vn.edu.eiu.testlab.fmecse456_2131200085.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.Equipment;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findByEquipmentType_EquipmentTypeId(Integer equipmentTypeId);
    
    @Query("SELECT e FROM Equipment e ORDER BY e.quantityAvailable DESC")
    List<Equipment> findAllOrderByQuantityAvailableDesc();
    
    @Query("SELECT e FROM Equipment e WHERE e.equipmentType.equipmentTypeId = :typeId ORDER BY e.quantityAvailable DESC")
    List<Equipment> findTop3ByEquipmentTypeOrderByQuantityAvailableDesc(Integer typeId);
    
    @Query("SELECT e FROM Equipment e WHERE LOWER(e.equipmentName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Equipment> findByEquipmentNameContainingIgnoreCase(String keyword);
}
