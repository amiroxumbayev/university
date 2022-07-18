package com.company.university.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.StudentDao;
import com.company.university.dao.GroupDao;
import com.company.university.domain.Group;
import com.company.university.domain.Page;
import com.company.university.domain.Schedule;
import com.company.university.domain.Student;
import com.company.university.service.StudentService;
import com.company.university.service.validator.StudentValidator;

@Service
public class StudentServiceImpl extends AbstractPersonServiceImpl implements StudentService {
    
    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final StudentValidator studentValidator;
    
    @Autowired
    public StudentServiceImpl(StudentDao studentDao, GroupDao groupDao,  StudentValidator studentValidator) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.studentValidator = studentValidator;
    }
    
    protected List<Schedule> findSchedule(Long id) {
        Group group = groupDao.findStudentGroupById(id).orElseThrow(DataBaseRuntimeException::new);
        List<Schedule> schedule = new ArrayList<>();
        group.getSchedule().values().forEach(e -> e.forEach(schedule::add));
        return schedule;
    }

    @Override
    public Long save(Student entity) {
        studentValidator.validate(entity);
        
        if (studentDao.findByEmail(entity.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The email has already been");
        }
        
        return studentDao.save(entity);
    }
    
    @Override
    public Student findById(Long id) {
        studentValidator.validateId(id);
        return studentDao.findById(id).orElseThrow(DataBaseRuntimeException::new);
    }

    @Override
    public List<Student> findAll() {
        List<Student> subjects = studentDao.findAll();

        if (subjects == null) {
            throw new DataBaseRuntimeException("Subjects are null");
        }
        return subjects;
    }

    @Override
    public List<Student> findAll(Page page) {

        studentValidator.validatePage(page);
        List<Student> items = studentDao.findAll(page);

        if (items == null) {
            throw new DataBaseRuntimeException("Items are null");
        }
        return items;
    }

    @Override
    public void update(Student entity) {
        studentValidator.validate(entity);
        
        if (!studentDao.findById(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Student not found");
        }
        
        studentDao.update(entity);
    }
    
    @Override
    public void deleteById(Long id) {
        studentValidator.validateId(id);

        if (!studentDao.findById(id).isPresent()) {
            throw new IllegalArgumentException("Subject not found");
        }

        studentDao.deleteById(id);
    }

}
