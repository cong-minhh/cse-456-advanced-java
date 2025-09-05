package vn.edu.eiu;

import vn.edu.eiu.model.Gender;
import vn.edu.eiu.model.Major;
import vn.edu.eiu.model.School;
import vn.edu.eiu.model.Student;
import vn.edu.eiu.service.SchoolService;
import vn.edu.eiu.service.StudentService;
import vn.edu.eiu.service.MajorService;
import vn.edu.eiu.service.impl.SchoolServiceImpl;
import vn.edu.eiu.service.impl.StudentServiceImpl;
import vn.edu.eiu.service.impl.MajorServiceImpl;
import vn.edu.eiu.util.HibernateUtil;

import java.time.LocalDate;

public class MainApp {
    private static final SchoolService schoolService = new SchoolServiceImpl();
    private static final MajorService majorService = new MajorServiceImpl();
    private static final StudentService studentService = new StudentServiceImpl();

    public static void main(String[] args) {
        try {
            // Test CRUD operations
//            testSchoolOperations();
//            testMajorOperations();
            testStudentOperations();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the EntityManagerFactory when done
            HibernateUtil.close();
        }
    }

    private static void testSchoolOperations() {
        System.out.println("\n=== Testing School Operations ===");

        // Create schools
        School school1 = School.builder().schoolId("S01").schoolName("School of IT").location("Building A").build();

        School school2 = School.builder().schoolId("S02").schoolName("School of Business").location("Building B").build();

        schoolService.save(school1);
        schoolService.save(school2);

        System.out.println("Created schools:");
        schoolService.findAll().forEach(System.out::println);

        // Update a school
        School schoolToUpdate = schoolService.findById("S01").orElse(null);
        if (schoolToUpdate != null) {
            schoolToUpdate.setLocation("Building C");
            schoolService.update(schoolToUpdate);
            System.out.println("\nUpdated school S01:" + schoolService.findById("S01"));
        }
    }

    private static void testMajorOperations() {
        System.out.println("\n=== Testing Major Operations ===");

        // Get existing schools
        School school1 = schoolService.findById("S01").orElseThrow();
        School school2 = schoolService.findById("S02").orElseThrow();

        // Create majors
        Major major1 = Major.builder().majorId("M001").majorName("Computer Science").school(school1).build();

        Major major2 = Major.builder().majorId("M002").majorName("Information Systems").school(school1).build();

        Major major3 = Major.builder().majorId("M003").majorName("Business Administration").school(school2).build();

        majorService.save(major1);
        majorService.save(major2);
        majorService.save(major3);

        System.out.println("Created majors:");
        majorService.findAll().forEach(System.out::println);

        // Update a major
        Major majorToUpdate = majorService.findById("M001").orElse(null);
        if (majorToUpdate != null) {
            majorToUpdate.setMajorName("Computer Science and Engineering");
            majorService.update(majorToUpdate);
            System.out.println("\nUpdated major M001:" + majorService.findById("M001"));
        }
    }

    private static void testStudentOperations() {
        System.out.println("\n=== Testing Student Operations ===");

        // Get existing school and major
        School school1 = schoolService.findById("S01").orElseThrow();
        Major major1 = majorService.findById("M001").orElseThrow();

        // Create students
        Student student1 = Student.builder().fullName("John Doe").gender(Gender.MALE).dob(LocalDate.of(2000, 5, 15)).gpa(3.8).major(major1).school(school1).enrollmentYear(2022).build();

        Student student2 = Student.builder().fullName("Jane Smith").gender(Gender.FEMALE).dob(LocalDate.of(2001, 8, 22)).gpa(3.9).major(major1).school(school1).enrollmentYear(2022).build();

        studentService.save(student1);
        studentService.save(student2);

        System.out.println("Created students:");
        studentService.findAll().forEach(System.out::println);

        // Update a student
        Student studentToUpdate = studentService.findById(1).orElse(null);
        if (studentToUpdate != null) {
            studentToUpdate.setGpa(3.95);
            studentService.update(studentToUpdate);
            System.out.println("\nUpdated student with ID 1:" + studentService.findById(1));
        }

        // Delete a student
        Student studentToDelete = studentService.findById(2).orElse(null);
        if (studentToDelete != null) {
            studentService.delete(studentToDelete);
            System.out.println("\nDeleted student with ID 2");
        }

        System.out.println("\nRemaining students:");
        studentService.findAll().forEach(System.out::println);
    }
}
