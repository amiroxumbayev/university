package com.company.university.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.SubjectDao;
import com.company.university.domain.Page;
import com.company.university.domain.Subject;
import com.company.university.service.SubjectService;
import com.company.university.service.validator.SubjectValidator;

@Service
public class SubjectServiceImpl implements SubjectService {
    
    private final SubjectDao subjectDao;
    private final SubjectValidator  subjectValidator;
    
    @Autowired
    public SubjectServiceImpl(SubjectDao subjectDao, SubjectValidator subjectValidator) {
        this.subjectDao = subjectDao;
        this. subjectValidator = subjectValidator;
    }

    @Override
    public Long save(Subject entity) {
         subjectValidator.validate(entity);
        
        if (subjectDao.findByName(entity.getName()).isPresent()) {
            throw new IllegalArgumentException("The name has already been");
        }
        
        return subjectDao.save(entity);
    }
    
    @Override
    public Subject findById(Long id) {
         subjectValidator.validateId(id);
        return subjectDao.findById(id).orElseThrow(DataBaseRuntimeException::new);
    }

    @Override
    public List<Subject> findAll() {
        List<Subject> subjects = subjectDao.findAll();

        if (subjects == null) {
            throw new DataBaseRuntimeException("Subjects are null");
        }
        return subjects;
    }

    @Override
    public List<Subject> findAll(Page page) {

         subjectValidator.validatePage(page);
        List<Subject> items = subjectDao.findAll(page);

        if (items == null) {
            throw new DataBaseRuntimeException("Items are null");
        }
        return items;
    }

    @Override
    public void update(Subject entity) {
         subjectValidator.validate(entity);
        
        if (!subjectDao.findById(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Subject not found");
        }
        
        subjectDao.update(entity);
    }
    
    @Override
    public void deleteById(Long id) {
         subjectValidator.validateId(id);

        if (!subjectDao.findById(id).isPresent()) {
            throw new IllegalArgumentException("Subject not found");
        }

        subjectDao.deleteById(id);
    }

}
