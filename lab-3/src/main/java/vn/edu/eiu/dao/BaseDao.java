package vn.edu.eiu.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    T update(T entity);
    void delete(T entity);
}
