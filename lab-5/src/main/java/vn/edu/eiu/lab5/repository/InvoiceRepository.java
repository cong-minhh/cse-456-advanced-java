package vn.edu.eiu.lab5.repository;

import org.springframework.stereotype.Repository;
import vn.edu.eiu.lab5.model.Invoice;

import java.util.List;

@Repository
public class InvoiceRepository extends BaseRepository<Invoice, Long> {
    
    public InvoiceRepository() {
        super(Invoice.class);
    }
    
    public List<Invoice> findByCustomerId(Long customerId) {
        return entityManager.createQuery(
                "SELECT i FROM Invoice i WHERE i.customer.id = :customerId ORDER BY i.issueDate DESC", 
                Invoice.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }
}
