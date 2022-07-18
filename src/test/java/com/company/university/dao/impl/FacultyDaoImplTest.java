package com.company.university.dao.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.FacultyDao;
import com.company.university.domain.Faculty;
import com.company.university.domain.Page;
import com.company.university.domain.Subject;

class FacultyDaoImplTest extends AbstractDaoImplTest {

    private FacultyDao facultyDao;

    @BeforeEach
    void setDao() {
        facultyDao = new FacultyDaoImpl(dataSource);
    }

    @Test
    void save_shouldInsertEntryToDB_whenStatementCorrect() {

        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(1L, "test", 1));
        Faculty faculty = new Faculty(null, "title", subjects);
        Long id = facultyDao.save(faculty);
        Faculty actual = facultyDao.findById(id).get();
        faculty = new Faculty(3L, "title", subjects);
        Faculty expected = faculty;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldFindEntryById_whenStatementCorrect() {

        Faculty actual = facultyDao.findById(1L).get();
        Faculty expected = new Faculty(1L, "test", new ArrayList<Subject>());
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldThrowNoSuchElementException_whenStatementIncorrect() {
        
        Throwable actual = assertThrows(NoSuchElementException.class, () -> {
            facultyDao.findById(3L).get();
        });
        String expected = "No value present";
        assertThat(actual.getMessage(), equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Faculty> actual = facultyDao.findAll();
        List<Faculty> expected = new ArrayList<>();
        expected.add(new Faculty(1L, "test", new ArrayList<Subject>()));
        expected.add(new Faculty(2L, "test1", new ArrayList<Subject>()));
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAllByPage_shouldFindCertainEntries_whenStatementCorrect() {
        Page page = new Page(2,1);
        List<Faculty> actual = facultyDao.findAll(page);
        List<Faculty> expected = new ArrayList<>();
        expected.add(new Faculty(2L, "test1", new ArrayList<Subject>()));
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Faculty updatedFactory = new Faculty(1L, "updatedtest", new ArrayList<Subject>());
        facultyDao.update(updatedFactory);
        Faculty actual = facultyDao.findById(1L).get();
        Faculty expected = updatedFactory;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldThrowDataBaseRuntimeException_whenStatementIncorrect() {
        Throwable actual = assertThrows(DataBaseRuntimeException.class, () -> {
            facultyDao.update(null);
        });
        String expected = "Failed to update entry in database";
        assertThat(actual.getMessage(), equalTo(expected));
    }
    
    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        facultyDao.deleteById(2L);
        List<Faculty> actual = facultyDao.findAll();
        int expected = 1;
        assertThat(actual.size(), equalTo(expected));
    }

}
