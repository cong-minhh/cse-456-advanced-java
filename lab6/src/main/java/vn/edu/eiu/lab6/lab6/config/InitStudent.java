package vn.edu.eiu.lab6.lab6.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.eiu.lab6.lab6.entity.Major;
import vn.edu.eiu.lab6.lab6.entity.Student;
import vn.edu.eiu.lab6.lab6.service.MajorService;
import vn.edu.eiu.lab6.lab6.service.StudentService;

@Component
public class InitStudent implements CommandLineRunner {

    @Autowired
    private StudentService studentService;

    @Autowired
    private MajorService majorService;

    @Override
    public void run(String... args) throws Exception {
        // Initialize majors if none exist
        if (majorService.getAllMajors().isEmpty()) {
            majorService.saveMajor(new Major(null, "Computer Science", "Study of computers and computational systems"));
            majorService.saveMajor(
                    new Major(null, "Information Technology", "Application of technology to solve business problems"));
            majorService
                    .saveMajor(new Major(null, "Software Engineering", "Systematic approach to software development"));
            majorService.saveMajor(new Major(null, "Data Science", "Extraction of knowledge from data"));
        }

        // Initialize students if none exist
        if (studentService.getAllStudents().isEmpty()) {
            studentService.saveStudent(new Student("Cong Minh", 2003, 3.6));
            studentService.saveStudent(new Student("Nguyen Van A", 2002, 3.4));
            studentService.saveStudent(new Student("Tran Thi B", 2003, 3.2));
            studentService.saveStudent(new Student("Le Van C", 2001, 3.1));
            studentService.saveStudent(new Student("Pham Thi D", 2003, 3.8));
        }
    }
}
