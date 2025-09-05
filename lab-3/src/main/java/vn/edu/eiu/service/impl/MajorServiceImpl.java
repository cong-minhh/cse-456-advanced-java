package vn.edu.eiu.service.impl;

import vn.edu.eiu.dao.MajorDao;
import vn.edu.eiu.dao.impl.MajorDaoImpl;
import vn.edu.eiu.model.Major;
import vn.edu.eiu.service.MajorService;

import java.util.List;
import java.util.Optional;

public class MajorServiceImpl implements MajorService {
    private final MajorDao majorDao;

    public MajorServiceImpl() {
        this.majorDao = new MajorDaoImpl();
    }

    @Override
    public Optional<Major> findById(String id) {
        return majorDao.findById(id);
    }

    @Override
    public List<Major> findAll() {
        return majorDao.findAll();
    }

    @Override
    public Major save(Major major) {
        return majorDao.save(major);
    }

    @Override
    public Major update(Major major) {
        return majorDao.update(major);
    }

    @Override
    public void delete(Major major) {
        majorDao.delete(major);
    }
}
