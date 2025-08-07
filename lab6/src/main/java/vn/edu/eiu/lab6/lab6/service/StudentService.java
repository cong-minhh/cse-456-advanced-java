package vn.edu.eiu.lab6.lab6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.lab6.lab6.entity.Student;
import vn.edu.eiu.lab6.lab6.repo.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return studentRepository.existsById(id);
    }
}