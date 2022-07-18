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

import com.company.university.domain.Subject;
import com.company.university.dao.SubjectDao;
import com.company.university.domain.Page;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {
    
    @Mock
    private SubjectDao subjectDao;
    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    void save_shouldSaveEntity_whenStatementCorrect() {
        when(subjectDao.save(new Subject(null, null, 0))).thenReturn(1L);
        Long actual = subjectService.save(new Subject(null, null, 0));
        Long expected = 1L;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldFindEntry_whenStatementCorrect() {
        Subject subject = new Subject(null, null, 0);
        when(subjectDao.findById(1L)).thenReturn(Optional.of(subject));
        Subject actual = subjectService.findById(1L);
        Subject expected = subject;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Subject> items = new ArrayList<>();
        items.add(new Subject(null, null, 0));
        when(subjectDao.findAll()).thenReturn(items);
        List<Subject> actual = subjectService.findAll();
        List<Subject> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntriesByPage_whenStatementCorrect() {
        List<Subject> items = new ArrayList<>();
        items.add(new Subject(null, null, 0));
        when(subjectDao.findAll(new Page(1,1))).thenReturn(items);
        List<Subject> actual = subjectService.findAll(new Page(1,1));
        List<Subject> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Subject subject = new Subject(null, null, 0);
        subjectService.update(subject);
        verify(subjectDao).update(subject);
    }
    
    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        subjectService.deleteById(1L);
        verify(subjectDao).deleteById(1L);
    }
}
