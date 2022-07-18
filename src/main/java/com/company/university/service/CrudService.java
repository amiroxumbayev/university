package com.company.university.service;

import java.util.List;

import com.company.university.domain.Page;

public interface CrudService<E> {
    Long save(E entity);
    E findById(Long id);
    List<E> findAll();
    List<E> findAll(Page page);
    void update(E entity);
    void deleteById(Long id);
    
}
