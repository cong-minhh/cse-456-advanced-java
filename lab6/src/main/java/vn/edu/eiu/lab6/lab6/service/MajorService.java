package vn.edu.eiu.lab6.lab6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.lab6.lab6.entity.Major;
import vn.edu.eiu.lab6.lab6.repo.MajorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MajorService {
    
    @Autowired
    private MajorRepository majorRepository;
    
    public List<Major> getAllMajors() {
        return majorRepository.findAll();
    }
    
    public Optional<Major> getMajorById(Long id) {
        return majorRepository.findById(id);
    }
    
    public Major saveMajor(Major major) {
        return majorRepository.save(major);
    }
    
    public void deleteMajor(Long id) {
        majorRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return majorRepository.existsById(id);
    }
}