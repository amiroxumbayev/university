package com.company.university.dao;

import java.util.Optional;

import com.company.university.domain.Subject;

public interface SubjectDao extends CrudDao<Subject> {
    
    Optional<Subject> findByName(String name);
}
