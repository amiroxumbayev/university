package com.company.university.dao;

import java.util.List;
import java.util.Optional;

import com.company.university.domain.Group;

public interface GroupDao extends CrudDao<Group> {
    
    List<Group> findTeacherGroupsById(Long id);
    
    Optional<Group> findStudentGroupById(Long id);
    
    Optional<Group> findByName(String name);
}
