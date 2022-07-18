package com.company.university.dao;

import java.util.Optional;

import com.company.university.domain.Student;

public interface StudentDao extends CrudDao<Student> {
    
    Optional<Student> findByEmail(String email);
}
