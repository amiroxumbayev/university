package com.company.university.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.GroupDao;
import com.company.university.domain.Group;
import com.company.university.domain.Page;
import com.company.university.service.GroupService;
import com.company.university.service.validator.GroupValidator;

@Service
public class GroupServiceImpl implements GroupService {
    
    private final GroupDao groupDao;
    private final GroupValidator groupValidator;
    
    @Autowired
    public GroupServiceImpl(GroupDao groupDao, GroupValidator groupValidator) {
        this.groupDao = groupDao;
        this.groupValidator = groupValidator;
    }

    @Override
    public Long save(Group group) {
        groupValidator.validate(group);
        
        if (groupDao.findByName(group.getName()).isPresent()) {
            throw new IllegalArgumentException("The name has already been");
        }
        
        return groupDao.save(group);
    }
    
    @Override
    public Group findById(Long id) {
        groupValidator.validateId(id);
        return groupDao.findById(id).orElseThrow(DataBaseRuntimeException::new);
    }

    @Override
    public List<Group> findAll() {
        List<Group> subjects = groupDao.findAll();

        if (subjects == null) {
            throw new DataBaseRuntimeException("Subjects are null");
        }
        return subjects;
    }

    @Override
    public List<Group> findAll(Page page) {

        groupValidator.validatePage(page);
        List<Group> items = groupDao.findAll(page);

        if (items == null) {
            throw new DataBaseRuntimeException("Items are null");
        }
        return items;
    }

    @Override
    public void update(Group group) {
        groupValidator.validate(group);
        
        if (!groupDao.findById(group.getId()).isPresent()) {
            throw new IllegalArgumentException("Group not found");
        }
        
        groupDao.update(group);
    }
    
    @Override
    public void deleteById(Long id) {
        groupValidator.validateId(id);

        if (!groupDao.findById(id).isPresent()) {
            throw new IllegalArgumentException("Subject not found");
        }

        groupDao.deleteById(id);
    }
    
}
