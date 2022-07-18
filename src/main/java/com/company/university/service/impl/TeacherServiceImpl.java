package com.company.university.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.GroupDao;
import com.company.university.dao.TeacherDao;
import com.company.university.domain.Group;
import com.company.university.domain.Page;
import com.company.university.domain.Schedule;
import com.company.university.domain.Teacher;
import com.company.university.service.TeacherService;
import com.company.university.service.validator.TeacherValidator;

@Service
public class TeacherServiceImpl extends AbstractPersonServiceImpl implements TeacherService {
    
    private final TeacherDao teacherDao;
    private final GroupDao groupDao;
    private final TeacherValidator teacherValidator;
    
    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao, GroupDao groupDao, TeacherValidator teacherValidator) {
        this.teacherDao = teacherDao;
        this.groupDao = groupDao;
        this.teacherValidator = teacherValidator;
    }
    
    protected List<Schedule> findSchedule(Long id) {
        List<Group> groups = groupDao.findTeacherGroupsById(id);
        List<List<Schedule>> scheduleByGroup = groups.stream()
                .map((Function<? super Group, ? extends List<Schedule>>) group -> {
                    List<Schedule> schedule = new ArrayList<>();
                    Collection<List<Schedule>> items= group.getSchedule().values();
                    items.forEach(e -> e.forEach(schedule::add));
                    return schedule;
                    })
                .collect(Collectors.toList());
        List<Schedule> schedule = new ArrayList<>();
        scheduleByGroup.forEach(items -> items.forEach(schedule::add));
        return schedule;
    }

    @Override
    public Long save(Teacher entity) {
        teacherValidator.validate(entity);
        
        if (teacherDao.findByEmail(entity.getEmail()).isPresent()) {
            throw new IllegalArgumentException("The email has already been");
        }
        
        return teacherDao.save(entity);
    }
    
    @Override
    public Teacher findById(Long id) {
        teacherValidator.validateId(id);
        return teacherDao.findById(id).orElseThrow(DataBaseRuntimeException::new);
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> subjects = teacherDao.findAll();

        if (subjects == null) {
            throw new DataBaseRuntimeException("Subjects are null");
        }
        return subjects;
    }

    @Override
    public List<Teacher> findAll(Page page) {

        teacherValidator.validatePage(page);
        List<Teacher> items = teacherDao.findAll(page);

        if (items == null) {
            throw new DataBaseRuntimeException("Items are null");
        }
        return items;
    }

    @Override
    public void update(Teacher entity) {
        teacherValidator.validate(entity);
        
        if (!teacherDao.findById(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Teacher not found");
        }
        
        teacherDao.update(entity);
    }
    
    @Override
    public void deleteById(Long id) {
        teacherValidator.validateId(id);

        if (!teacherDao.findById(id).isPresent()) {
            throw new IllegalArgumentException("Subject not found");
        }

        teacherDao.deleteById(id);
    }
    
}
