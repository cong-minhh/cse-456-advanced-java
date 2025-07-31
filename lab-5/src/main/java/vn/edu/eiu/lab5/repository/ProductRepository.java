package vn.edu.eiu.lab5.repository;

import org.springframework.stereotype.Repository;
import vn.edu.eiu.lab5.model.Product;

import java.util.List;

@Repository
public class ProductRepository extends BaseRepository<Product, Long> {
    
    public ProductRepository() {
        super(Product.class);
    }
    
    public List<Product> findByNameContaining(String name) {
        return entityManager.createQuery(
                "SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(:name)", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
