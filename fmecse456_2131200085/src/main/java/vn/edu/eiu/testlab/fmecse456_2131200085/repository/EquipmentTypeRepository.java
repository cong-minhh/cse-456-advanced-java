package vn.edu.eiu.testlab.fmecse456_2131200085.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.EquipmentType;

import java.util.Optional;

public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Integer> {
    Optional<EquipmentType> findByTypeName(String typeName);
}
