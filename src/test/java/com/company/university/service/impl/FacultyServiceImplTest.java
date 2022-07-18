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

import com.company.university.domain.Page;
import com.company.university.dao.FacultyDao;
import com.company.university.domain.Faculty;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    
    @Mock
    private FacultyDao facultyDao;
    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void save_shouldSaveEntity_whenStatementCorrect() {
        when(facultyDao.save(new Faculty(null, null, null))).thenReturn(1L);
        Long actual = facultyService.save(new Faculty(null, null, null));
        Long expected = 1L;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldFindEntry_whenStatementCorrect() {
        Faculty faculty = new Faculty(null, null, null);
        when(facultyDao.findById(1L)).thenReturn(Optional.of(faculty));
        Faculty actual = facultyService.findById(1L);
        Faculty expected = faculty;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Faculty> items = new ArrayList<>();
        items.add(new Faculty(null, null, null));
        when(facultyDao.findAll()).thenReturn(items);
        List<Faculty> actual = facultyService.findAll();
        List<Faculty> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntriesByPage_whenStatementCorrect() {
        List<Faculty> items = new ArrayList<>();
        items.add(new Faculty(null, null, null));
        when(facultyDao.findAll(new Page(1,1))).thenReturn(items);
        List<Faculty> actual = facultyService.findAll(new Page(1,1));
        List<Faculty> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Faculty faculty = new Faculty(null, null, null);
        facultyService.update(faculty);
        verify(facultyDao).update(faculty);
    }
    
    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        facultyService.deleteById(1L);
        verify(facultyDao).deleteById(1L);
    }

}
