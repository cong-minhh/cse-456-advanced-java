package vn.edu.eiu.service.impl;

import vn.edu.eiu.dao.StudentDao;
import vn.edu.eiu.dao.impl.StudentDaoImpl;
import vn.edu.eiu.model.Student;
import vn.edu.eiu.service.StudentService;

import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public Student save(Student student) {
        return studentDao.save(student);
    }

    @Override
    public Student update(Student student) {
        return studentDao.update(student);
    }

    @Override
    public void delete(Student student) {
        studentDao.delete(student);
    }
}
