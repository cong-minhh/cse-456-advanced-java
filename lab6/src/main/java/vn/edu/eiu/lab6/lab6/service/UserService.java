package vn.edu.eiu.lab6.lab6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.eiu.lab6.lab6.entity.User;
import vn.edu.eiu.lab6.lab6.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}