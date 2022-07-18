package com.company.university.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.FacultyDao;
import com.company.university.domain.Faculty;
import com.company.university.domain.Page;
import com.company.university.service.FacultyService;
import com.company.university.service.validator.FacultyValidator;

@Service
public class FacultyServiceImpl implements FacultyService {
    
    private final FacultyDao facultyDao;
    private final FacultyValidator facultyValidator;
    
    @Autowired
    public FacultyServiceImpl(FacultyDao facultyDao, FacultyValidator facultyValidator) {
        this.facultyDao = facultyDao;
        this.facultyValidator = facultyValidator;
    }

    @Override
    public Long save(Faculty entity) {
        facultyValidator.validate(entity);
        
        if (facultyDao.findByTitle(entity.getTitle()).isPresent()) {
            throw new IllegalArgumentException("The title has already been");
        }
        
        return facultyDao.save(entity);
    }
    
    @Override
    public Faculty findById(Long id) {
        facultyValidator.validateId(id);
        return facultyDao.findById(id).orElseThrow(DataBaseRuntimeException::new);
    }

    @Override
    public List<Faculty> findAll() {
        List<Faculty> subjects = facultyDao.findAll();

        if (subjects == null) {
            throw new DataBaseRuntimeException("Subjects are null");
        }
        return subjects;
    }

    @Override
    public List<Faculty> findAll(Page page) {

        facultyValidator.validatePage(page);
        List<Faculty> items = facultyDao.findAll(page);

        if (items == null) {
            throw new DataBaseRuntimeException("Items are null");
        }
        return items;
    }

    @Override
    public void update(Faculty entity) {
        facultyValidator.validate(entity);
        
        if (!facultyDao.findById(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Faculty not found");
        }
        
        facultyDao.update(entity);
    }

    @Override
    public void deleteById(Long id) {
        facultyValidator.validateId(id);

        if (!facultyDao.findById(id).isPresent()) {
            throw new IllegalArgumentException("Subject not found");
        }

        facultyDao.deleteById(id);
    }

}
