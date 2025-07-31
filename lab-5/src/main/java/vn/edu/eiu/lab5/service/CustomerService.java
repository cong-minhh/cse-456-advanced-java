package vn.edu.eiu.lab5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eiu.lab5.model.Customer;
import vn.edu.eiu.lab5.repository.CustomerRepository;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        // Check if customer with this email already exists
        Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer != null && !existingCustomer.getId().equals(customer.getId())) {
            throw new IllegalStateException("Email already in use");
        }
        
        if (customer.getId() == null) {
            customerRepository.save(customer);
            return customer;
        } else {
            return customerRepository.update(customer);
        }
    }

    public Customer update(Customer customer) {
        return save(customer);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
