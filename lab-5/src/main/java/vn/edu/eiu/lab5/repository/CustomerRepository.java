package vn.edu.eiu.lab5.repository;

import org.springframework.stereotype.Repository;
import vn.edu.eiu.lab5.model.Customer;

@Repository
public class CustomerRepository extends BaseRepository<Customer, Long> {
    
    public CustomerRepository() {
        super(Customer.class);
    }
    
    public Customer findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT c FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
