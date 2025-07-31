package vn.edu.eiu.lab5.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public abstract class BaseRepository<T, ID> {
    @PersistenceContext
    protected EntityManager entityManager;
    
    private final Class<T> entityClass;
    
    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }
    
    public List<T> findAll() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return entityManager.createQuery(jpql, entityClass).getResultList();
    }
    
    public void save(T entity) {
        entityManager.persist(entity);
    }
    
    public T update(T entity) {
        return entityManager.merge(entity);
    }
    
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
    
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }
}
