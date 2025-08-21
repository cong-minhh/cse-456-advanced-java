package vn.edu.eiu.testlab.fmecse456_2131200085.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
}
