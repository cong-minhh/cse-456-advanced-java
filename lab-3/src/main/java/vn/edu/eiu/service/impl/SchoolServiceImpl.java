package vn.edu.eiu.service.impl;

import vn.edu.eiu.dao.SchoolDao;
import vn.edu.eiu.dao.impl.SchoolDaoImpl;
import vn.edu.eiu.model.School;
import vn.edu.eiu.service.SchoolService;

import java.util.List;
import java.util.Optional;

public class SchoolServiceImpl implements SchoolService {
    private final SchoolDao schoolDao;

    public SchoolServiceImpl() {
        this.schoolDao = new SchoolDaoImpl();
    }

    @Override
    public Optional<School> findById(String id) {
        return schoolDao.findById(id);
    }

    @Override
    public List<School> findAll() {
        return schoolDao.findAll();
    }

    @Override
    public School save(School school) {
        return schoolDao.save(school);
    }

    @Override
    public School update(School school) {
        return schoolDao.update(school);
    }

    @Override
    public void delete(School school) {
        schoolDao.delete(school);
    }
}
