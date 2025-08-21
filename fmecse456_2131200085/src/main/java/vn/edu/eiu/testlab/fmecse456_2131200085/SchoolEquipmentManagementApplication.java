package vn.edu.eiu.testlab.fmecse456_2131200085;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("vn.edu.eiu.testlab.fmecse456_2131200085.entity")
@EnableJpaRepositories("vn.edu.eiu.testlab.fmecse456_2131200085.repository")
public class SchoolEquipmentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolEquipmentManagementApplication.class, args);
    }
}
