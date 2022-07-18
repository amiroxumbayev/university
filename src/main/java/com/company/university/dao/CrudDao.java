package com.company.university.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.company.university.domain.Page;

public interface CrudDao<E> {
       
    Long save(E entity);
    
    Optional<E> findById(Long id);
    
    List<E> findAll();
    
    List<E> findAll(Page page);
    
    void update(E entity);
    
    void deleteById(Long id);
}
