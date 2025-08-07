package vn.edu.eiu.lab6.lab6.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.eiu.lab6.lab6.entity.Major;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}