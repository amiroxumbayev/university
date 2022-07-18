package com.company.university.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.university.dao.GroupDao;
import com.company.university.domain.Group;
import com.company.university.domain.Page;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {
    
    @Mock
    private GroupDao groupDao;
    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    void save_shouldSaveEntity_whenStatementCorrect() {
        when(groupDao.save(Group.builder().build())).thenReturn(1L);
        Long actual = groupService.save(Group.builder().build());
        Long expected = 1L;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldFindEntry_whenStatementCorrect() {
        Group group = Group.builder().build();
        when(groupDao.findById(1L)).thenReturn(Optional.of(group));
        Group actual = groupService.findById(1L);
        Group expected = group;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Group> items = new ArrayList<>();
        items.add(Group.builder().build());
        when(groupDao.findAll()).thenReturn(items);
        List<Group> actual = groupService.findAll();
        List<Group> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntriesByPage_whenStatementCorrect() {
        List<Group> items = new ArrayList<>();
        items.add(Group.builder().build());
        when(groupDao.findAll(new Page(1,1))).thenReturn(items);
        List<Group> actual = groupService.findAll(new Page(1,1));
        List<Group> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Group group = Group.builder().build();
        groupService.update(group);
        verify(groupDao).update(group);
    }
    
    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        groupService.deleteById(1L);
        verify(groupDao).deleteById(1L);
    }

}
