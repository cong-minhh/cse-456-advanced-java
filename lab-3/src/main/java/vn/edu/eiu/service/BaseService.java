package vn.edu.eiu.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    T update(T entity);
    void delete(T entity);
}
