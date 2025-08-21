package vn.edu.eiu.testlab.fmecse456_2131200085.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.Equipment;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.EquipmentType;
import vn.edu.eiu.testlab.fmecse456_2131200085.entity.User;
import vn.edu.eiu.testlab.fmecse456_2131200085.repository.EquipmentRepository;
import vn.edu.eiu.testlab.fmecse456_2131200085.repository.EquipmentTypeRepository;
import vn.edu.eiu.testlab.fmecse456_2131200085.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

        private final EquipmentTypeRepository equipmentTypeRepository;
        private final EquipmentRepository equipmentRepository;
        private final UserRepository userRepository;

        @Autowired
        public DataInitializer(EquipmentTypeRepository equipmentTypeRepository,
                        EquipmentRepository equipmentRepository,
                        UserRepository userRepository) {
                this.equipmentTypeRepository = equipmentTypeRepository;
                this.equipmentRepository = equipmentRepository;
                this.userRepository = userRepository;

        }

        @PostConstruct
        public void init() {
                // Only initialize if database is empty
                if (equipmentTypeRepository.count() == 0) {
                        initEquipmentTypes();
                }
                if (equipmentRepository.count() == 0) {
                        initEquipment();
                }
                if (userRepository.count() == 0) {
                        initUsers();
                }
        }

        private void initEquipmentTypes() {
                EquipmentType laptop = new EquipmentType();
                laptop.setTypeName("Laptop");
                laptop.setDescription("Portable computers used for teaching/research");
                equipmentTypeRepository.save(laptop);

                EquipmentType projector = new EquipmentType();
                projector.setTypeName("Projector");
                projector.setDescription("Devices used for classroom presentations");
                equipmentTypeRepository.save(projector);

                EquipmentType printer = new EquipmentType();
                printer.setTypeName("Printer");
                printer.setDescription("Printers for administrative/student use");
                equipmentTypeRepository.save(printer);

                EquipmentType microscope = new EquipmentType();
                microscope.setTypeName("Microscope");
                microscope.setDescription("Lab equipment for biology/medical courses");
                equipmentTypeRepository.save(microscope);
        }

        private void initEquipment() {
                // Get equipment types
                EquipmentType laptopType = equipmentTypeRepository.findByTypeName("Laptop")
                                .orElseThrow(() -> new IllegalStateException("Laptop type not found"));
                EquipmentType projectorType = equipmentTypeRepository.findByTypeName("Projector")
                                .orElseThrow(() -> new IllegalStateException("Projector type not found"));
                EquipmentType printerType = equipmentTypeRepository.findByTypeName("Printer")
                                .orElseThrow(() -> new IllegalStateException("Printer type not found"));
                EquipmentType microscopeType = equipmentTypeRepository.findByTypeName("Microscope")
                                .orElseThrow(() -> new IllegalStateException("Microscope type not found"));

                // Create equipment
                Equipment eq1 = createEquipment("EQ00000001", laptopType, "Dell Latitude 5420",
                                new BigDecimal("1500.00"), 20, LocalDateTime.of(2025, 8, 21, 8, 0));

                Equipment eq2 = createEquipment("EQ00000002", laptopType, "HP ProBook 450 G8",
                                new BigDecimal("1350.00"), 15, LocalDateTime.of(2025, 8, 21, 8, 5));

                Equipment eq3 = createEquipment("EQ00000003", projectorType, "Epson EB-X06 Projector",
                                new BigDecimal("2200.00"), 5, LocalDateTime.of(2025, 8, 21, 8, 10));

                Equipment eq4 = createEquipment("EQ00000004", projectorType, "BenQ MW550 Projector",
                                new BigDecimal("2500.00"), 3, LocalDateTime.of(2025, 8, 21, 8, 15));

                Equipment eq5 = createEquipment("EQ00000005", printerType, "Canon LBP2900 Printer",
                                new BigDecimal("1200.00"), 10, LocalDateTime.of(2025, 8, 21, 8, 20));

                Equipment eq6 = createEquipment("EQ00000006", microscopeType, "Olympus CX23 Microscope",
                                new BigDecimal("5000.00"), 7, LocalDateTime.of(2025, 8, 21, 8, 25));

                // Save all equipment
                equipmentRepository.save(eq1);
                equipmentRepository.save(eq2);
                equipmentRepository.save(eq3);
                equipmentRepository.save(eq4);
                equipmentRepository.save(eq5);
                equipmentRepository.save(eq6);
        }

        private Equipment createEquipment(String id, EquipmentType type, String name,
                        BigDecimal price, int quantity, LocalDateTime purchaseDate) {
                Equipment equipment = new Equipment();
                equipment.setEquipmentId(id);
                equipment.setEquipmentType(type);
                equipment.setEquipmentName(name);
                equipment.setPurchasePrice(price);
                equipment.setQuantityAvailable(quantity);
                equipment.setPurchaseDate(purchaseDate);
                return equipment;
        }

        private void initUsers() {
                // Admin user
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin");
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);

                // Staff user
                User staff = new User();
                staff.setUsername("staff01");
                staff.setPassword("staff");
                staff.setRole(User.Role.STAFF);
                userRepository.save(staff);

                // Customer user
                User customer = new User();
                customer.setUsername("customer");
                customer.setPassword("customer");
                customer.setRole(User.Role.CUSTOMER);
                userRepository.save(customer);
        }
}
