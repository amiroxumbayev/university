package com.company.university.dao;

import java.util.Optional;

import com.company.university.domain.Faculty;

public interface FacultyDao extends CrudDao<Faculty> {

    Optional<Faculty> findByTitle(String title);
}
