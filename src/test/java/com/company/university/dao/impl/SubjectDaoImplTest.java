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
import com.company.university.dao.SubjectDao;
import com.company.university.domain.Page;
import com.company.university.domain.Subject;

class SubjectDaoImplTest extends AbstractDaoImplTest{

    private SubjectDao subjectDao;

    @BeforeEach
    void setDao() {
        subjectDao = new SubjectDaoImpl(dataSource);
    }

    @Test
    void save_shouldInsertEntryToDB_whenStatementCorrect() {
        
        Subject subject = new Subject(null, "name", 1);
        Long id = subjectDao.save(subject);
        Subject actual = subjectDao.findById(id).get();
        subject = new Subject(3L, "name", 1);
        Subject expected = subject;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldFindEntryById_whenStatementCorrect() {

        Subject actual = subjectDao.findById(1L).get();
        Subject expected = new Subject(1L, "test", 1);
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldThrowNoSuchElementException_whenStatementIncorrect() {
        
        Throwable actual = assertThrows(NoSuchElementException.class, () -> {
            subjectDao.findById(3L).get();
        });
        String expected = "No value present";
        assertThat(actual.getMessage(), equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Subject> actual = subjectDao.findAll();
        List<Subject> expected = new ArrayList<>();
        expected.add(new Subject(1L, "test", 1));
        expected.add(new Subject(2L, "test1", 1));
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAllByPage_shouldFindCertainEntries_whenStatementCorrect() {
        Page page = new Page(2,1);
        List<Subject> actual = subjectDao.findAll(page);
        List<Subject> expected = new ArrayList<>();
        expected.add(new Subject(2L, "test1", 1));
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Subject updatedFactory = new Subject(1L, "updatedtest", 1);
        subjectDao.update(updatedFactory);
        Subject actual = subjectDao.findById(1L).get();
        Subject expected = updatedFactory;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldThrowDataBaseRuntimeException_whenStatementIncorrect() {
        Throwable actual = assertThrows(DataBaseRuntimeException.class, () -> {
            subjectDao.update(null);
        });
        String expected = "Failed to update entry in database";
        assertThat(actual.getMessage(), equalTo(expected));
    }
    
    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        subjectDao.deleteById(2L);
        List<Subject> actual = subjectDao.findAll();
        int expected = 1;
        assertThat(actual.size(), equalTo(expected));
    }

}
