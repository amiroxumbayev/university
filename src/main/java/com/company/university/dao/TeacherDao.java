package com.company.university.dao;

import java.util.Optional;

import com.company.university.domain.Teacher;

public interface TeacherDao extends CrudDao<Teacher> {
        
    Optional<Teacher> findByEmail(String email);
}
